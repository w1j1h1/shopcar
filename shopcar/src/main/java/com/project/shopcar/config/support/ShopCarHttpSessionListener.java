package com.project.shopcar.config.support;

import com.project.shopcar.utils.LogUtil;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ShopCarHttpSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        LogUtil.info("sesison会话创建:" + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        LogUtil.info("sesison会话销毁:" + se.getSession().getId());
    }
}
