package com.alon.ssh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by zhangyl on 2019/5/10
 */
@SpringBootApplication
@EnableTransactionManagement
public class WebSSHApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WebSSHApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebSSHApplication.class);
    }
}
