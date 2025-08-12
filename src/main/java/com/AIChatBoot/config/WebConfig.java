package com.AIChatBoot.config;

import com.AIChatBoot.security.SimpleRateLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<SimpleRateLimitFilter> rateLimitFilter() {
        FilterRegistrationBean<SimpleRateLimitFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SimpleRateLimitFilter());
        registrationBean.addUrlPatterns("/api/gemini/*"); // âœ… Swagger & /v3/api-docs are NOT intercepted
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
