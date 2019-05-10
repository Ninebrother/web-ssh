package com.alon.ssh.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by zhangyl on 2019/4/22
 */
@Getter
@Setter
public class SSHLog {
    /**
     * 日志ID
     */
    private Long logId;
    /**
     * 命令/记录
     */
    private String data;
    /**
     * 命令时间
     */
    private Date cmdTime;
    /**
     * 记录时间
     */
    private String recordTime;
    /**
     * 登录退出时间
     */
    private Date endTime;
}
