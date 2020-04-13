package com.project.shopcar.controller;

import com.project.shopcar.config.Constant;
import com.project.shopcar.config.SessionConfig;
import com.project.shopcar.mapper.TestMapper;
import com.project.shopcar.utils.LogUtil;
import com.project.shopcar.utils.RedisUtils;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/test")
public class testController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(HttpServletRequest request, HttpServletResponse response) {
        String aa = request.getParameter("aa");
        System.out.println(aa);
        System.out.println(aa);
        HttpSession session = request.getSession();
        System.out.println(testMapper.getTestAll().get(0).toString());

//        try {
//            int a = 5 / 0;
//        } catch (Exception e) {
//            LogUtil.exceptionLog(e, getClass());
//            LogUtil.info("ceshi", getClass());
//        }
        return "index";
    }

    @RequestMapping("/del")
    @ResponseBody
    public String deleteRedisSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        RedisUtils redisUtils = new RedisUtils();
        //使session无效，删除session会话
        session.invalidate();
//        System.out.println(session.getId());
//        System.out.println(redisUtils.hasKey("spring:session:sessions:expires:"+session.getId()));
//        redisUtils.del("spring:session:sessions:expires:"+session.getId());
        return "delete success";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request) {
        String sj = request.getParameter("sj");
        String token = UUID.randomUUID().toString();
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        redisUtils.set(sj + "_token", token, Constant.token_expire_time);
        return "login success";
    }

    @RequestMapping("/online")
    @ResponseBody
    public String online() {
        return "当前在线人数：" + SessionConfig.online;
    }
}
