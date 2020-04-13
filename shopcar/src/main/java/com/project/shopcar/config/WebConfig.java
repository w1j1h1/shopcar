package com.project.shopcar.config;

import com.project.shopcar.config.support.ShopCarFilter;
import com.project.shopcar.config.support.ShopCarHttpSessionListener;
import com.project.shopcar.config.support.ShopCarInterceptor;
import com.project.shopcar.config.support.ShopCarServletContextListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.SessionCookieConfig;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.TimeUnit;

@Configuration
public class WebConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //addResourceHandler请求路径
//        //addResourceLocations 在项目中的资源路径
//        //setCacheControl 设置静态资源缓存时间
//        //可以设置默认的资源路径，在配置文件里头设置
////        registry.addResourceHandler("/static/**","/templates/**").addResourceLocations("classpath:/static/","classpath:/templates/")
////                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
//                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
//        WebMvcConfigurer.super.addResourceHandlers(registry);
//    }

    //这里使用bean注入的原因是，如果拦截器里头要使用到bean注入的方式调用一些工具，这里也需要用bean注入的方式才能再返回拦截器的时候，返回的那个拦截器里头的bean是不为null的
    @Bean
    public ShopCarInterceptor getShopCarInterceptor() {
        return new ShopCarInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getShopCarInterceptor())
                .addPathPatterns("/**").excludePathPatterns("/test/login")
                //访问静态资源不进行拦截
                .excludePathPatterns("/favicon.ico", "/js/**", "/css/**", "/image/**", "/webjars/**", "/error");
    }

    @Bean
    public FilterRegistrationBean shopCarFilter() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new ShopCarFilter());
        frBean.addUrlPatterns("/*");
        return frBean;
    }

    //注入字符编码过滤器
    @Bean
    public FilterRegistrationBean characterEncodingFilter() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setForceEncoding(true);
        characterEncodingFilter.setEncoding("UTF-8");
        frBean.setFilter(characterEncodingFilter);
        frBean.addUrlPatterns("/*");
        return frBean;
    }

    //注入servletContext监听器
    @Bean
    public ServletListenerRegistrationBean servletContextListenerRegist() {
        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
        srb.setListener(new ShopCarServletContextListener());
        return srb;
    }

    //采用了spring-session，此处监听不到session的事件
//    @Bean
//    public ServletListenerRegistrationBean httpSessionListenerRegist() {
//        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
//        srb.setListener(new ShopCarHttpSessionListener());
//        return srb;
//    }
}
