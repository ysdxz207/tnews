/**
 * 
 */
package com.puyixiaowo.tnews.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.puyixiaowo.tnews.common.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangfeihong
 * @date 2016年12月8日 下午10:20:18
 */
public class PageBean extends ResponseBean{
	private static final long serialVersionUID = 4728599360521173588L;
	private int pageSize = Constants.DEFAULT_PAGE_SIZE;
	private int pageCurrent = 1;
	private int totalCount = 0;
	private PageRowBounds pageRowBounds;
	private List<?> list = new ArrayList<Object>();

	
	/**
	 * 
	 */
	public PageBean() {
		this.pageRowBounds = buidRowBounds();
	}
	
	/**
	 * 
	 */
	public PageBean(int pageCurrent, int pageSize) {
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
		this.pageRowBounds = buidRowBounds();
		this.setMessage("");//关闭成功提示框
	}
	
	/**
	 * 
	 */
	public PageBean(boolean selectAll) {
		if (selectAll) {
			this.pageRowBounds = new PageRowBounds();
		} else {
			this.pageRowBounds = buidRowBounds();
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageRowBounds getPageRowBounds() {
		return pageRowBounds;
	}

	public void setPageRowBounds(PageRowBounds pageRowBounds) {
		this.pageRowBounds = pageRowBounds;
	}

	public int getPageCurrent() {
		return pageCurrent;
	}

	public void setPageCurrent(int pageCurrent) {
		this.pageCurrent = pageCurrent;
	}

	
	
	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	///////////////////////
	/**
	 * 序列化
	 * @param list
	 */
	public void setSerializeList(List<?> list) {
		
		this.list = JSONObject.parseArray(JSON.toJSONString(list));
	}
	
	
	private PageRowBounds buidRowBounds() {
		int offset = (pageCurrent - 1) * pageSize ;
		PageRowBounds bouds = new PageRowBounds(offset, pageSize);
		if (!bouds.equals(pageRowBounds)) {
			return bouds;
		}
		
		return pageRowBounds;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> getRecordList(Class clazz) throws Exception{
//		List<T> dest = new ArrayList<T>();
//		for (Object obj : recordList) {
//			if (obj != null && obj.getClass() == clazz) {
//				dest.add((T) obj);
//			}
//		}
		if (list == null) {
			return null;
		}
		if (list.size() == 0) {
			return new ArrayList<T>();
		}
		Class c = list.get(0).getClass();
		if (clazz != c) {
			throw new Exception("转换list失败:目标Class:" + clazz + "不等于源Class:" + c);
		}
		return (List<T>) list;
	}

	@Override
	public String serialize() {
		return JSON.toJSONString(this);
	}
}
