package com.project.shopcar.config.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class ShopCarAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //此处可以根据角色进行不同的登录页面跳转
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String path = httpServletRequest.getContextPath() ;
        String basePath = httpServletRequest.getScheme()+"://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+path+"/";
//        System.out.println(path);
//        System.out.println(basePath);
        if (!roles.contains("ROLE_admin")) {
            httpServletResponse.sendRedirect("/test/businessLogin");
            return;
        }
        httpServletResponse.sendRedirect("/test/hello");
    }
}
