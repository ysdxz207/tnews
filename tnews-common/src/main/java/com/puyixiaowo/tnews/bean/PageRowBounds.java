package com.puyixiaowo.tnews.bean;

import java.io.Serializable;

import org.apache.ibatis.session.RowBounds;

public class PageRowBounds extends RowBounds implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1432242532800200927L;
	
	public PageRowBounds() {
		super();
	}
	
	public PageRowBounds(int offset, int limit) {
		super(offset, limit);
	}

	

}
