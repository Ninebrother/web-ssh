package com.alon.ssh.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhangyl on 2019/4/4
 */
@Getter
@Setter
public class AuthUser {
    private String hostIp;
    private int port;
    private String userName;
    private String authType;
    private String password;
    private String privateKey;

    /**
     * 日志Id
     */
    private Long logId;
}
