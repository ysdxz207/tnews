package com.puyixiaowo.tnews.news.webmagic;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.HasKey;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;
import us.codecraft.webmagic.pipeline.JsonFilePageModelPipeline;

@TargetUrl("http://news.sohu.com/20170406/n486806677.shtml")
@HelpUrl("http://news.sohu.com/20170406/n486806677.shtml")
public class GithubRepo implements HasKey{

    @ExtractBy(value = "//h1[@itemprop='headline']/text()")
	private String title;

    @ExtractBy(value = "//div[@id='contentText']/div/tidyText()")
    private String context;

    @ExtractBy("//div[@class='source']/tidyText()")
    private String source;

    public static void main(String[] args) {
        //
        OOSpider.create(Site.me().setSleepTime(1000)
                , new JsonFilePageModelPipeline(), GithubRepo.class)
                .addUrl("http://news.sohu.com/20170406/n486806677.shtml").thread(5).run();
    }

    @Override
    public String key() {

        return "myfilename";
    }

    ////////////////////////////
    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }

    public String getSource() {
        return source;
    }
}