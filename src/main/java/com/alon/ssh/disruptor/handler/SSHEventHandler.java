package com.alon.ssh.disruptor.handler;

import com.alon.ssh.enums.SSHType;
import com.lmax.disruptor.EventHandler;
import com.alon.ssh.disruptor.event.SSHEvent;
import com.alon.ssh.service.ISSHLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhangyl on 2019/4/26
 */
@Component
public class SSHEventHandler implements EventHandler<SSHEvent> {

    @Autowired
    private ISSHLogService logService;

    @Override
    public void onEvent(SSHEvent sshEvent, long l, boolean b) {
        if (sshEvent.getSshType().equals(SSHType.RECORD)) {
            logService.insertRecord(sshEvent.getLog());
        } else if (sshEvent.getSshType().equals(SSHType.END)) {
            logService.updateEnd(sshEvent.getLog());
        } else if (sshEvent.getSshType().equals(SSHType.CMD)) {
            logService.insertCmd(sshEvent.getLog());
        }
    }
}
