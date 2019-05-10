package com.alon.ssh.disruptor.translator;

import com.alon.ssh.enums.SSHType;
import com.alon.ssh.model.SSHLog;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.alon.ssh.disruptor.event.SSHEvent;

/**
 * Created by zhangyl on 2019/4/26
 */
public class SSHEventTranslator implements EventTranslatorOneArg<SSHEvent, SSHLog> {

    private SSHType sshType;

    public SSHEventTranslator(SSHType sshType) {
        this.sshType = sshType;
    }

    @Override
    public void translateTo(SSHEvent sshEvent, long l, SSHLog sshLog) {
        sshEvent.setLog(sshLog);
        sshEvent.setSshType(sshType);
    }
}
