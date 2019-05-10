package com.alon.ssh.service;

import com.alon.ssh.enums.AuthType;
import com.alon.ssh.model.AuthUser;
import org.springframework.stereotype.Service;

/**
 * Created by zhangyl on 2019/4/8
 */
@Service
public class AuthService implements IAuthService {
    @Override
    public AuthUser selectByHostIdAndUserId(String hostIP, String userId) {
        AuthUser authUser=new AuthUser();
        authUser.setHostIp("172.24.133.70");
        authUser.setPort(22);
        authUser.setUserName("logs");
        authUser.setAuthType(AuthType.PASSWORD.getCode());
        authUser.setPassword("yg123456");
        return authUser;
    }
}
