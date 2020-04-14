package com.project.shopcar.config;

import com.project.shopcar.config.support.ShopCarAuthenticationFailureHandler;
import com.project.shopcar.config.support.ShopCarAuthenticationSuccessHandler;
import com.project.shopcar.service.ShopCarUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

//@Configuration
@EnableWebSecurity//该注解包括Configuration注解的作用
//@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法授权的注解，在有configuration注解的类添加该注解即可
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {

    //动态加盐加密
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ShopCarUserDetailsService getShopCarUserDetailsService() {
        return new ShopCarUserDetailsService();
    }

    @Bean
    public AuthenticationSuccessHandler getShopCarAuthenticationSuccessHandler() {
        return new ShopCarAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler getShopCarAuthenticationFailureHandler() {
        return new ShopCarAuthenticationFailureHandler();
    }

    @Autowired
    private DataSource dataSource;   //是在application.properites

    /**
     * 记住我功能的token存取器配置
     * @return
     */
    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //定制请求规则
        http.formLogin()//  定义当需要用户登录时候，转到的登录页面
                .usernameParameter("userName")//自定义页面里头表单提交用的控件name
                .passwordParameter("passWord")//自定义页面里头表单提交用的控件name
                .loginPage("/test/userLogin")// 设置登录页面，默认去/test/userLogin，失败去/login?error
                .loginProcessingUrl("/user/login")// 自定义的登录接口，登录页面post提交的
//                .failureUrl("/test/adminLogin")//登录失败转发接口
                .failureHandler(getShopCarAuthenticationFailureHandler())//自定义根据不同条件跳转不同页面
                //登录成功转发接口，加了true则不管之前访问的是什么url，统一跳转这个，否则跳转之前访问的url
//                .defaultSuccessUrl("/test/businessLogin",true)
                .successHandler(getShopCarAuthenticationSuccessHandler())//自定义根据不同条件跳转不同页面
                .and()
                .csrf().disable()// 关闭csrf防护
                .logout()//注销功能，去/login?logout
                .logoutSuccessUrl("/test/userLogin")//设置注销后跳转的页面
                .and()
//                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())   //前后端分离时返回403，拒绝
//                .and()
                .rememberMe()
                .rememberMeParameter("rememberMe")//自定义页面里头表单提交控件的rememberme的name
                .userDetailsService(userDetailsService())
                .tokenRepository(getPersistentTokenRepository())
                .tokenValiditySeconds(Constant.token_expire_time)
                .and()
                .authorizeRequests()// 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/test/userLogin", "/test/adminLogin", "/test/businessLogin").permitAll()// 设置所有人都可以访问登录页面
//                .antMatchers("/test/**").hasAuthority("admin")    //这是web授权,url添加权限。方法授权和url授权会是并集的结果
                .antMatchers("/test/**").hasAuthority("admin")    //这是web授权,url添加权限。方法授权和url授权会是并集的结果
//                .antMatchers("/test/**").hasRole("admin")         //这是url添加角色，采用角色方式在userdetailsservice返回的数据需要有ROLE_XXXX
                .anyRequest()// 任何请求,登录后可以访问
                .authenticated();//用户需要登录才能访问
        //session管理
        //session失效后跳转
//        http.sessionManagement().invalidSessionUrl("/login");
//        //只允许一个用户登录,如果同一个账户两次登录,那么第一个账户将被踢下线,跳转到登录页面
//        http.sessionManagement().maximumSessions(1).expiredUrl("/login");
        //解决不允许显示在iframe的问题
//        http.headers().frameOptions().disable();
    }

    //定制认证规则
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用userDetailsService
        auth.userDetailsService(getShopCarUserDetailsService());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //设置静态资源不要拦截，也可以写在configure(HttpSecurity http)当中允许所有人访问
        web.ignoring().antMatchers("/favicon.ico", "/js/**", "/css/**", "/image/**", "/webjars/**", "/error");
    }
}
