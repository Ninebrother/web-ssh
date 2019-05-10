package com.alon.ssh.disruptor.event;

import com.alon.ssh.enums.SSHType;
import com.alon.ssh.model.SSHLog;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by zhangyl on 2019/4/26
 */
@Getter
@Setter
public class SSHEvent implements Serializable {
    private SSHLog log;
    private SSHType sshType;

}
