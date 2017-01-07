package com.hilrara.kingdom.config;

import com.hilrara.kingdom.interceptor.XTokenAttachInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by kun7788 on 16. 6. 8..
 */
@Configuration
public class PreprocessorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new XTokenAttachInterceptor());
    }
}
