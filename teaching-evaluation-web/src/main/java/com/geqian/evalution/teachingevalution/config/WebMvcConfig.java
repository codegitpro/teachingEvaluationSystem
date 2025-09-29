package com.geqian.evalution.teachingevalution.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${local-storage.file.save-path}")
    private String savePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置图片静态资源处理器
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + savePath);
    }
}
