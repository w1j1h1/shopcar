package com.project.shopcar.controller;

import com.project.shopcar.config.Constant;
import com.project.shopcar.config.SessionConfig;
import com.project.shopcar.mapper.TestMapper;
import com.project.shopcar.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/test")
//@PreAuthorize("hasAuthority('admin1')")//方法授权注解，可以写在controller上，也可以写在controller内部方法里头
public class testController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/hello")
    @ResponseBody
//    @PreAuthorize("hasAuthority('admin1')")
    public String hello(HttpServletRequest request, HttpServletResponse response) {
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

    @RequestMapping("userLogin")
    public String userLogin() {
        System.out.println("userLogin");
        return "userLogin";
    }

    @RequestMapping("adminLogin")
    public String adminLogin() {
        System.out.println("adminLogin");
        return "adminLogin";
    }

    @RequestMapping("businessLogin")
    public String businessLogin() {
        System.out.println("businessLogin");
        return "businessLogin";
    }
}
