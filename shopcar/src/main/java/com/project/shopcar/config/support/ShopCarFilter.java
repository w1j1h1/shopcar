package com.project.shopcar.config.support;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ShopCarFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String url = request.getRequestURI();
//        System.out.println("filter:   "+url);
        if (url.indexOf("/css/") == -1 && url.indexOf("/js/") == -1
                && url.indexOf("/image/") == -1 && url.indexOf("/favicon.ico") == -1
                && url.indexOf("/webjars/") == -1 && url.indexOf("/error") == -1) {
//            System.out.println("这里是过滤器");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
