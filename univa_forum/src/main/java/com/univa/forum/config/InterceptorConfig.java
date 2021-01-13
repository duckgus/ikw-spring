package com.univa.forum.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.univa.forum.interceptor.LoginInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
//			.addPathPatterns("/**")
//			.excludePathPatterns("/vendors/**","/src/**","/dist/**") // static 폴더
//			.excludePathPatterns("/forum","/forum/board","/forum/signup","/forum/signin"); // rest api
			.addPathPatterns("/**")
			.excludePathPatterns("/vendors/**","/src/**","/dist/**","/favicon.ico","/widget/**") // static 폴더
			.excludePathPatterns(
					"/",
					"/forum",
					"/forum/main",
					"/forum/main/board",
					"/forum/main/content",
					"/forum/main/history",
					"/forum/main/profile",
					"/forum/main/like",
					"/forum/signup",
					"/forum/signin",
					"/forum/service/**",
					
					"/forum/img",
					"/forum/getfile"
			); // rest api
	}
}
