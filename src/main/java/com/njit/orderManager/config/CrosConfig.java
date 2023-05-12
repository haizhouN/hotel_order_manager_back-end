package com.njit.orderManager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CrosConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//配置可以被跨域的路径
                .allowedOriginPatterns("*")//浏览器允许所有的域访问/*不能满足带有cookie的访问，Origin必须是全匹配
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")//允许请求方法访问该跨域资源服务器
                .allowCredentials(true)//允许带cookie访问
                .maxAge(3600)
                .allowedHeaders("*");//请求方法访问该跨域资源服务器
        }
}
