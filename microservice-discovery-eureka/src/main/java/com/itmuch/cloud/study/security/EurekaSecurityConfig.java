package com.itmuch.cloud.study.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 在服务端增加配置类（spring boot 2.X 版本必加，不然会客户端注册到eureka的时候会报错 Cannot execute request on any known server ）
 *
 * @author yanbdong@cienet.com.cn
 * @since Nov 13, 2020
 */
@EnableWebSecurity
public class EurekaSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//		http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
