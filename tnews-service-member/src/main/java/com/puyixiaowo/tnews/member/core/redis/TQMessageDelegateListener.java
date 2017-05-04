package com.puyixiaowo.tnews.member.core.redis;

public class TQMessageDelegateListener {



	public void handleMessage(String message) {

		System.out.println("recieve message:" + message);
	}
}
