package com.alon.ssh.disruptor.publisher;

import com.alon.ssh.disruptor.handler.SSHEventHandler;
import com.alon.ssh.disruptor.translator.SSHEventTranslator;
import com.alon.ssh.enums.SSHType;
import com.alon.ssh.model.SSHLog;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.alon.ssh.disruptor.event.SSHEvent;
import com.alon.ssh.disruptor.factory.SSHEventFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zhangyl on 2019/4/26
 */
@Component
public class SSHEventPublisher implements DisposableBean {

    private Disruptor<SSHEvent> disruptor;

    @Autowired
    private SSHEventHandler eventHandler;

    public static final int BUFFER_SIZE=1024;
    /**
     * start disruptor.
     *
     */
    public void start() {
        disruptor = new Disruptor<>(new SSHEventFactory(), BUFFER_SIZE, r -> {
            AtomicInteger index = new AtomicInteger(1);
            return new Thread(null, r, "disruptor-thread-" + index.getAndIncrement());
        }, ProducerType.MULTI, new BlockingWaitStrategy());
        disruptor.handleEventsWith(eventHandler);
        disruptor.start();
    }


    /**
     * publish disruptor event.
     * @param sshLog
     * @param sshType
     */
    public void publishEvent(final SSHLog sshLog, final SSHType sshType) {
        final RingBuffer<SSHEvent> ringBuffer = disruptor.getRingBuffer();
        ringBuffer.publishEvent(new SSHEventTranslator(sshType), sshLog);
    }


    @Override
    public void destroy() {

    }
}
