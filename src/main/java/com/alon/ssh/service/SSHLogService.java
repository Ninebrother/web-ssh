package com.alon.ssh.service;

import com.alibaba.fastjson.JSON;
import com.alon.ssh.model.SSHLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by zhangyl on 2019/4/22
 */
@Service
public class SSHLogService implements ISSHLogService {
    public static final Logger logger=LoggerFactory.getLogger(SSHLogService.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public int insertRecord(SSHLog sshLog) {
        mongoTemplate.insert(sshLog);
        if(logger.isInfoEnabled()){
            logger.info("mongodb插入WebSSH记录:" + JSON.toJSONString(sshLog));
        }
        return 1;
    }

    @Override
    public int insertCmd(SSHLog sshLog) {
        System.out.println("持久化Cmd:"+JSON.toJSON(sshLog).toString());
        return 0;
    }

    @Override
    public int updateEnd(SSHLog sshLog) {
        System.out.println("退出刷新"+JSON.toJSON(sshLog).toString());
        return 0;
    }
}
