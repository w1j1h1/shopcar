package com.project.shopcar.service;

import com.alibaba.fastjson.JSONObject;
import com.project.shopcar.utils.LogUtil;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.swing.plaf.basic.BasicViewportUI;

@Component
public class ShopCarUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LogUtil.info(userName);
        //这里可以进行查询数据库，然后将数据放入user的构造方法中。第三个参数可以为多种权限或者角色
        User user = new User(userName, "$2a$10$MzDJQrJObrzHBHYXe09mP.GyMCDo0s5IZyNoNQOXAV9B7/.59qm1i",
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin,admin"));
        return user;
    }
}
