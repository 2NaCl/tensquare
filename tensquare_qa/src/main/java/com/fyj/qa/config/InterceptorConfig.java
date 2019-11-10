package com.fyj.qa.config;

import com.fyj.qa.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器要声明拦截的url和拦截的http请求
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")  //拦截所有的url
                .excludePathPatterns("/**/login/**");  //除了login

    }
}
