package com.alon.ssh.common;

import com.alon.ssh.ws.SSHClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhangyl on 2019/4/4
 */
public class SessionContextHolder {
    public static Map<String, SSHClient> ONLINE_USERS = new ConcurrentHashMap<>();

    public static void put(String sessionId, SSHClient sshClient) {
        ONLINE_USERS.put(sessionId, sshClient);
    }

    public static void remove(String sessionId) {
        ONLINE_USERS.remove(sessionId);
    }

    public static SSHClient get(String sessionId) {
        return ONLINE_USERS.get(sessionId);
    }
}
