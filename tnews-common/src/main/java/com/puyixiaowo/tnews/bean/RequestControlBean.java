package com.puyixiaowo.tnews.bean;

import java.io.Serializable;

/**
 * 访问接口请求控制
 * @author huangfeihong
 * @date 2017-04-07 11:58
 */
public class RequestControlBean implements Serializable{
	private static final long serialVersionUID = -6518822128236209801L;
	private long time;
	private int times;

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
}
