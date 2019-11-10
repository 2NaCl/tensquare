package com.fyj.user.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //当我们需要具体配置的时候，就把空方法拿出来进行配置

    /**
     * authorizeRequests所有security全注解配置实现的开端，表示开始说明需要的权限。
     * 需要的权限分两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限
     * antMatches表示拦截什么路径，permitAll表示什么url可以通行
     * anyRequests任何的请求，authenticated认证后才能访问
     * .and().csrf().disable()固定写法，表示csrf拦截失效
     *
     * @param http
     * @throws Exception
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
