package com.alon.ssh.ws;

import com.alon.ssh.common.PatternUtil;
import com.alon.ssh.common.SessionContextHolder;
import com.alon.ssh.enums.AuthType;
import com.alon.ssh.enums.SSHType;
import com.alon.ssh.model.AuthUser;
import com.alon.ssh.model.SSHLog;
import com.alon.ssh.disruptor.publisher.SSHEventPublisher;
import com.alon.ssh.service.IAuthService;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.ParameterMap;
import org.yeauty.pojo.Session;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created by zhangyl on 2019/4/28
 * 当ServerEndpointExporter类通过Spring配置进行声明并被使用，它将会去扫描带有@ServerEndpoint注解的类
 * 被注解的类将被注册成为一个WebSocket端点 所有的配置项都在这个注解的属性中 ( 如:@ServerEndpoint("/ws") )
 */
@ServerEndpoint(prefix = "netty-websocket")
@Component
public class WebSocket {

    public static final Logger logger = LoggerFactory.getLogger(WebSocket.class);

    @Autowired
    private SSHEventPublisher eventPublisher;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private IAuthService authService;

    private static StringBuffer CMD_BUF = new StringBuffer();

    /**
     * 当有新的WebSocket连接进入时，对该方法进行回调
     *
     * @param session
     * @param headers
     * @param parameterMap
     * @throws Exception
     */
    @OnOpen
    public void onOpen(Session session, HttpHeaders headers, ParameterMap parameterMap) throws Exception {
//        获取remoteAddress  IP地址
//        String loginIp= session.channel().remoteAddress().toString();
        String hostId = parameterMap.getParameter("hostId");
        String userId = parameterMap.getParameter("userId");
        Long logId = Long.parseLong(parameterMap.getParameter("logId"));
        AuthUser authUser = authService.selectByHostIdAndUserId(hostId, userId);
        authUser.setLogId(logId);
        session.setAttribute("logId", logId);
        String hostIp = authUser.getHostIp();
        int port = authUser.getPort();
        String userName = authUser.getUserName();
        String password = authUser.getPassword();

        AuthType authType = AuthType.verify(authUser.getAuthType());
        session.sendText(this.getBanner().toString());

        //判断登录方式
        SSHClient client = new SSHClient();
        if (AuthType.PASSWORD.equals(authType)) {
            client = client.connectWithPassWord(hostIp, port, userName, password);

        } else if (AuthType.SSH_KEY.equals(authType)) {
            client = client.connectWithSSHKey(hostIp, port, userName, authUser.getPrivateKey(), password);
        }

        if (client == null) {
            session.sendText("连接服务器失败！！");
            session.close();
            return;
        }
        eventPublisher.start();
        SessionContextHolder.put(session.id().toString(), client);
        long startTime = System.currentTimeMillis();

        executor.execute(() -> sendMessage(session, logId, startTime));
    }

    /**
     * 发送到xterm的日志
     *
     * @param session
     * @param logId
     * @param startTime
     */
    private void sendMessage(final Session session, long logId, final long startTime) {
        int bufferSize = 8192;
        byte[] buffer = new byte[8192];
        try {

            SSHClient client = SessionContextHolder.get(session.id().toString());
            while (session.isOpen()) {
                int length = client.getSession().getStdout().read(buffer, 0, bufferSize);
                if (length <= -1) {
                    break;
                }
                String data = new String(buffer, 0, length, StandardCharsets.UTF_8);
                session.sendText(data);

                //记录日志，用于录像回放
                SSHLog log = new SSHLog();
                log.setLogId(logId);
                log.setData(data);
                log.setRecordTime(System.currentTimeMillis() - startTime + "");
                eventPublisher.publishEvent(log, SSHType.RECORD);

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * 当接收到字符串消息时，对该方法进行回调
     *
     * @param session
     * @param message
     * @throws IOException
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        if (logger.isInfoEnabled()) {
            logger.info("接受xterm输入的命令:" + message);
        }
        SSHClient client = SessionContextHolder.get(session.id().toString());
        if (client != null) {
            client.write(message);
        }
        CMD_BUF.append(message);
        if (PatternUtil.isEnter(message)) {
            /**
             * log  异步记录日志
             */
            Long logId = session.getAttribute("logId");
            SSHLog log = new SSHLog();
            log.setLogId(logId);
            log.setData(CMD_BUF.toString());
            log.setCmdTime(new Date());
            eventPublisher.publishEvent(log, SSHType.CMD);
            CMD_BUF = new StringBuffer();
        }
    }

    /**
     * 当有WebSocket连接关闭时，对该方法进行回调
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        logger.error("afterConnectionClosed");
        SSHClient client = SessionContextHolder.get(session.id().toString());
        if (client != null) {
            client.close();
            SessionContextHolder.remove(session.id().toString());
        }
        updateEndTime(session);
    }

    /**
     * 当有WebSocket抛出异常时，对该方法进行回调
     *
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.error("handleTransportError", throwable);
        SSHClient client = SessionContextHolder.get(session.id().toString());
        if (client != null) {
            client.close();
            SessionContextHolder.remove(session.id().toString());
        }
        updateEndTime(session);
    }

    /**
     * 当接收到二进制消息时，对该方法进行回调
     *
     * @param session
     * @param bytes
     */
    @OnBinary
    public void onBinary(Session session, byte[] bytes) {
        for (byte b : bytes) {
            System.out.println("bvbbbb:" + b);
        }
        session.sendBinary(bytes);
    }

    /**
     * 当接收到Netty的事件时，对该方法进行回调
     *
     * @param session
     * @param evt
     */
    @OnEvent
    public void onEvent(Session session, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    System.out.println("read idle");
                    break;
                case WRITER_IDLE:
                    System.out.println("write idle");
                    break;
                case ALL_IDLE:
                    System.out.println("all idle");
                    break;
                default:
                    break;
            }
        }
    }

    private StringBuilder getBanner() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\u001b[32mWelcome to WebSSH.\r\n");
        buffer.append("Copyright © 2019 https://www.yingu.com/.\r\n");
        buffer.append("\u001b[31m-----------------------------------------------------\r\n");
        buffer.append("\u001b[0m");
        return buffer;
    }

    private void updateEndTime(Session session) {
        /**
         * socket关闭，更新结束时间
         */
        Long logId = session.getAttribute("logId");
        SSHLog log = new SSHLog();
        log.setLogId(logId);
        log.setEndTime(new Date());
        eventPublisher.publishEvent(log, SSHType.END);
    }
}
