package com.puyixiaowo.tnews.manager.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;

import com.puyixiaowo.tnews.common.utils.RedisUtils;
/**
 * 重写shiro session dao，session保存到redis
 * @author huangfeihong
 * @date 2016年12月20日
 */
public class RedisSessionDao extends AbstractSessionDAO {

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public void update(Session session) throws UnknownSessionException {
		//System.out.println("更新seesion,id=" + session.getId().toString());
		try {
			redisUtils.set(session.getId().toString(), session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Session session) {
		//System.out.println("删除seesion,id=" + session.getId().toString());
		try {
			redisUtils.delete(session.getId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public Collection<Session> getActiveSessions() {
		//System.out.println("获取存活的session");
		return Collections.emptySet();
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		//System.out.println("创建seesion,id=" + session.getId().toString());
		try {
			redisUtils.set(sessionId.toString(), session);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {

		//System.out.println("获取seesion,id=" + sessionId.toString());
		Session session = null;
		try {
			session = (Session) redisUtils.get(sessionId.toString());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return session;
	}
}