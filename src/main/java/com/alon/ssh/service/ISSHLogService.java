package com.alon.ssh.service;

import com.alon.ssh.model.SSHLog;

/**
 * Created by zhangyl on 2019/4/22
 */
public interface ISSHLogService {
    /**
     * 记录记录，用于录像回放
     * @param sshLog
     * @return
     */
    int insertRecord(SSHLog sshLog);

    /**
     * 记录记录，用于录像回放
     * @param sshLog
     * @return
     */
    int insertCmd(SSHLog sshLog);

    /**
     * 更新
     * @param sshLog
     * @return
     */
    int updateEnd(SSHLog sshLog);
}
