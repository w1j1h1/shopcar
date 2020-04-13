package com.project.shopcar.config;

import com.project.shopcar.config.support.ShopCarHttpSessionListener;
import com.project.shopcar.utils.LogUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.session.web.http.SessionEventHttpSessionListenerAdapter;

import javax.servlet.http.HttpSessionListener;
import java.util.ArrayList;
import java.util.List;

@Configuration
//这里设置session的过期时间，会将配置文件中的设置覆盖，不设置会取默认的1800
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = Constant.token_expire_time)
public class SessionConfig {
    //因为implements  HttpSessionListener类的接口只有监听创建和销毁两种，这里采用sessionconfig的三种监听
//    @Bean
//    public SessionEventHttpSessionListenerAdapter sessionEventHttpSessionListenerAdapter() {
//        List<HttpSessionListener> list = new ArrayList<>();
//        list.add(new ShopCarHttpSessionListener());
//        return new SessionEventHttpSessionListenerAdapter(list);
//    }

    //一个简单的监听当前在线人数
    public static int online = 0;

    /**
      * Redis内session过期事件监听
      */
    @EventListener
    public void onSessionExpired(SessionExpiredEvent expiredEvent) {
        String sessionId = expiredEvent.getSessionId();
        LogUtil.info("session会话过期:" + sessionId);
        online--;
    }


    /**
      * Redis内session删除事件监听
      */
    @EventListener
    public void onSessionDeleted(SessionDeletedEvent deletedEvent) {
        String sessionId = deletedEvent.getSessionId();
        LogUtil.info("session会话被删除:" + sessionId);
        online--;
    }

    /**
      * Redis内session保存事件监听
      */
    @EventListener
    public void onSessionCreated(SessionCreatedEvent createdEvent) {
        String sessionId = createdEvent.getSessionId();
        LogUtil.info("session会话被创建:" + sessionId);
        online++;
    }
}


////采用了redis做session共享，会导致原来的session的监听器监听不到，可采用这三种方式进行监听
//@Configuration
//public class SessionConfig  extends RedisHttpSessionConfiguration {
//    public SessionConfig(){
//        List<HttpSessionListener> list = new ArrayList<>();
//        list.add(new ShopCarHttpSessionListener());
//        this.setHttpSessionListeners(list);
//    }
//
//    // 添加session 监听
//    @Override
//    public void setHttpSessionListeners(List<HttpSessionListener> listeners) {
//        super.setHttpSessionListeners(listeners);
//    }
//
//    //设置session过期时间
//    @Override
//    public void setMaxInactiveIntervalInSeconds(int maxInactiveIntervalInSeconds) {
//        super.setMaxInactiveIntervalInSeconds(maxInactiveIntervalInSeconds);
//    }
//}
