package com.alon.ssh.service;

import com.alon.ssh.model.AuthUser;

/**
 * Created by zhangyl on 2019/4/4
 */
public interface IAuthService {
    /**
     * 根据主机ID和用户ID查找认证用户信息
     * @param hostId
     * @param userId
     * @return
     */
    AuthUser selectByHostIdAndUserId(String hostId, String userId);
}
