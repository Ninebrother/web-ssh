package com.alon.ssh.disruptor.factory;

import com.alon.ssh.disruptor.event.SSHEvent;
import com.lmax.disruptor.EventFactory;

/**
 * Created by zhangyl on 2019/4/26
 */
public class SSHEventFactory implements EventFactory<SSHEvent> {
    @Override
    public SSHEvent newInstance() {
        return new SSHEvent();
    }
}
