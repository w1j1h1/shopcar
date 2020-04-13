package com.project.shopcar.config.support;

import com.project.shopcar.utils.LogUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ShopCarServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LogUtil.info("shopcar项目启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LogUtil.info("shopcar项目关闭");
    }
}
