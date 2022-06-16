//package com.project.pokemon.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class Webconfig implements WebMvcConfigurer {
//
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowedOriginPatterns("*")
//                .allowedOrigins("*") //배포이후 설정 필요
//                .allowCredentials(true)
//                .exposedHeaders("Authorization");
//    }
//
//}
