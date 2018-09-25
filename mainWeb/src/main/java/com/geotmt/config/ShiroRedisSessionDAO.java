package com.geotmt.config;

import com.geotmt.common.utils.RedisKeys;
import com.geotmt.commons.RedisService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * shiro session dao
 * session 本地化
 * 如果不自己注入sessionDAO，defaultWebSessionManager会使用MemorySessionDAO做为默认实现类
 * https://blog.csdn.net/lishehe/article/details/45223823
 */
@Component
public class ShiroRedisSessionDAO extends EnterpriseCacheSessionDAO {
    @Autowired
    private RedisService redisService;

    //创建session
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);
        final String key = RedisKeys.getShiroSessionKey(sessionId.toString());
        setShiroSession(key, session);
        return sessionId;
    }

    //获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = super.doReadSession(sessionId);
        if(session == null){
            final String key = RedisKeys.getShiroSessionKey(sessionId.toString());
            session = getShiroSession(key);
        }
        return session;
    }

    //更新session
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        final String key = RedisKeys.getShiroSessionKey(session.getId().toString());
        setShiroSession(key, session);
    }

    //删除session
    @Override
    protected void doDelete(Session session) {
        super.doDelete(session);
        final String key = RedisKeys.getShiroSessionKey(session.getId().toString());
        redisService.remove(key);
    }

    private Session getShiroSession(String key) {
        Boolean flag = redisService == null ;
        Object session = redisService.get(key);
        if(session == null){
            return null;
        }else {
            return (Session)session;
        }
    }

    private void setShiroSession(String key, Session session){
        redisService.set(key, session,60 * 1000L);
    }

}
