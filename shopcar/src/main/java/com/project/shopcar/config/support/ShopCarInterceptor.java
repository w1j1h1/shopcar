package com.project.shopcar.config.support;

import com.project.shopcar.config.Constant;
import com.project.shopcar.utils.LogUtil;
import com.project.shopcar.utils.RedisUtils;
import io.lettuce.core.output.ScanOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

public class ShopCarInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
        //token令牌，用于前后端分离token验证
//        String sj = request.getParameter("sj");
//        String redisToken = (String) redisUtils.get(sj + "_token") == null ? " " : (String) redisUtils.get(sj + "_token");
//        HttpSession session = request.getSession();
//        String sessionToken = (String) session.getAttribute("token");
//        System.out.println("interceptor:   "+request.getRequestURI());
//        if (redisToken.equals(sessionToken)) {
//            redisUtils.set(sj + "_token", redisToken, Constant.token_expire_time);
//            return true;
//        } else {
////            LogUtil.info("bei lan jie le ");
//            System.out.println("这里拦截了");
//            return true;
//        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
