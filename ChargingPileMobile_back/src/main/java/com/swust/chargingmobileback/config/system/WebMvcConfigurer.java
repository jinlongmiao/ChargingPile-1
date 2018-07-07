package com.swust.chargingmobileback.config.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * spring boot 配置
 * @author 73559
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurationSupport {

    @Bean
    public HandlerInterceptor getInterceptor(){
        return new Interceptor();
    }

    /**
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getInterceptor()).addPathPatterns("/**").excludePathPatterns("/login/**");
        super.addInterceptors(registry);
    }

}
