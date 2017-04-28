package com.puyixiaowo.tnews.news.core.thread;

import com.puyixiaowo.tnews.news.bean.NewsBean;
import com.puyixiaowo.tnews.news.service.impl.NewsFilterService;
import com.puyixiaowo.tnews.news.utils.ApplicationContextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * 关键词获取新闻线程
 *
 * @author huangfeihong
 * @date 2017-03-15 15:54:25
 */
public class NewsByFilterThread extends RecursiveTask<List<NewsBean>> {

	private NewsFilterService newsFilterService = ApplicationContextUtil.getBean("newsFilterService", NewsFilterService.class);

	private String[] dictionarys;
	private Long newsChannelId;
	private boolean isMainThread;//是否主线程
	private int i;//子线程索引


	public NewsByFilterThread(String[] dictionarys, Long newsChannelId, int i, boolean isMainThread) {
		this.dictionarys = dictionarys;
		this.newsChannelId = newsChannelId;
		this.isMainThread = isMainThread;
		this.i = i;
	}

	@Override
	protected List<NewsBean> compute() {

		List<NewsBean> list = new ArrayList<>();

		if (i == dictionarys.length) {
			//处理到结尾
			return list;
		}
		if (!isMainThread) {
			//子线程直接返回处理结果
			long start = System.currentTimeMillis();
			list = newsFilterService.requestKeyWordsFilterNews(dictionarys[i], newsChannelId);
			long end = System.currentTimeMillis();
			//System.out.println(dictionarys[i] + ",i=" + i + ",条数：" + list.size() + ",消耗时间：" + (end-start) + "毫秒");

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return list;
		}
		
		List<NewsByFilterThread> threads = new ArrayList<>();

		for (int j = 0; j < dictionarys.length; j++) {

			NewsByFilterThread newsByFilterThread = new NewsByFilterThread(dictionarys, newsChannelId, j, false);//子线程
			threads.add(newsByFilterThread);
			newsByFilterThread.fork();
		}

		for (NewsByFilterThread newsByFilterThread :
				threads) {
			list.addAll(newsByFilterThread.join());
		}
		
		return list;
	}


}
