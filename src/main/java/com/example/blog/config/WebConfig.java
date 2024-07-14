package com.example.blog.config;

import com.example.blog.filter.SessionCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<SessionCheckFilter> sessionCheckFilter() {
        FilterRegistrationBean<SessionCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionCheckFilter());
        registrationBean.addUrlPatterns("/*"); // フィルタを適用するパスパターンを指定

        // ログイン画面にはフィルタを適用しない
        registrationBean.setName("sessionCheckFilter");
        registrationBean.setOrder(1); // フィルタの順序を設定

        return registrationBean;
    }
}
