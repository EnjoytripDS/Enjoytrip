package com.ssafy.enjoytrip.commons.config;

import com.ssafy.enjoytrip.commons.auth.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JwtInterceptor())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/**/user/*", "/**/user/login", "/**/user/check/*",
//                        "/**/user/logout/*", "/**/user/refresh");
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("access-token", "refresh-token", "Content-Type");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        String resourcePattern = "/image/" + "**";
        registry.addResourceHandler(resourcePattern)
                .addResourceLocations("file:///" + "C:/travelary/image/");
    }

}
