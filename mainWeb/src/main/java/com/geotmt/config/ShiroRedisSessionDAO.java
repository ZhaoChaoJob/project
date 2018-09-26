package com.geotmt.config;

import com.geotmt.common.utils.RedisKeys;
import com.geotmt.commons.RedisService;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

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
        Object session = redisService.get(key);
        if(session == null){
            return null;
        }else {
            return byteToSession((byte[])session);
        }
    }

    private void setShiroSession(String key, Session session){
        redisService.set(key, sessionToByte(session),60 * 1000L);
    }
    // 把session对象转化为byte保存到redis中
    private byte[] sessionToByte(Session session){
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        byte[] bytes = null;
        try {
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(session);
            bytes = bo.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // 把byte还原为session
    private Session byteToSession(byte[] bytes){
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream in;
        SimpleSession session = null;
        try {
            in = new ObjectInputStream(bi);
            session = (SimpleSession) in.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return session;
    }
}
