package com.puyixiaowo.tnews.news.core.redis;

public class TQMessageDelegateListener {



	public void handleMessage(String message) {

		System.out.println("recieve message:" + message);
	}
}
