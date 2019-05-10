package com.alon.ssh.controller;

import com.alon.ssh.model.HostInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyl on 2019/5/10
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/index.htm",method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        List<HostInfo> list=new ArrayList<>();
        list.add(HostInfo.builder().id(1L).name("测试服务器").hostAddress("172.24.133.70").portNumber(22).build());
        modelMap.addAttribute("listData", list);
        return "index";
    }

    @RequestMapping(value = "/terminal/{hostId}.htm",method = RequestMethod.GET)
    public String terminal(@PathVariable Long hostId, ModelMap modelMap) {
        modelMap.addAttribute("hostId", hostId);
        modelMap.addAttribute("userId", 1L);
        modelMap.addAttribute("hostName", "测试服务器");
        modelMap.addAttribute("hostIP", "172.24.133.70");
        //TODO 登录成功，添加WebSSH日志。这个方法不能删除
        modelMap.addAttribute("logId", 10001L);
        return "terminal";
    }

}
