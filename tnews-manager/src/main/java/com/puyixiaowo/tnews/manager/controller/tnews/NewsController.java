package com.puyixiaowo.tnews.manager.controller.tnews;

import com.alibaba.fastjson.JSON;
import com.puyixiaowo.tnews.bean.PageBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.manager.constants.RoutesNews;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.manager.shiro.SubjectManager;
import com.puyixiaowo.tnews.manager.utils.QiniuUtils;
import com.puyixiaowo.tnews.news.bean.NewsBean;
import com.puyixiaowo.tnews.news.bean.NewsChannelBean;
import com.puyixiaowo.tnews.news.service.NewsApiService;
import com.puyixiaowo.tnews.news.service.NewsChannelService;
import com.puyixiaowo.tnews.news.service.NewsService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 新闻
 *
 * @author huangfeihong
 * @date 2017年1月11日
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

    @Autowired
    private NewsService newsService;
    @Autowired
    private NewsChannelService newsChannelService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private NewsApiService newsApiService;

    @RequiresPermissions("news:view")
    @RequestMapping("/{data}")
    @ResponseBody
    public Object news(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       NewsBean newsBean,
                       @PathVariable Boolean data) {
        if (!data) {
            Object obj = redisUtils.get(RedisKeys.SWITCH_API.key);
            Object objFilter = redisUtils.get(RedisKeys.SWITCH_API_FILTER.key);
            Object objAuto = redisUtils.get(RedisKeys.SWITCH_API_AUTO.key);

            model.addAttribute("switchApiFilter", Boolean.valueOf(objFilter == null ? "true" : objFilter.toString()));
            model.addAttribute("switchApi", Boolean.valueOf(obj == null ? "true" : obj.toString()));
            model.addAttribute("switchAuto", Boolean.valueOf(objAuto == null ? "true" : objAuto.toString()));
            return new ModelAndView(RoutesNews.NEWS_LIST);
        }

        PageBean pageBean = getPageBean(request);

        try {

            List<NewsBean> list = newsService.selectByExampleAndRowBounds(newsBean, pageBean.getPageRowBounds());
            pageBean.setList(list);
            pageBean.setTotalCount(newsService.selectCountByExample(newsBean));
        } catch (RuntimeException e) {
            pageBean.error(e.getMessage());
        }
        return pageBean.serialize();
    }

    @RequiresPermissions("news:view")
    @RequestMapping(value = "/detail/{newsId}", method = {RequestMethod.POST, RequestMethod.GET})
    public Object detail(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         @PathVariable Long newsId) {

        //查询新闻详情
        NewsBean bean = newsService.selectByPrimaryKey(newsId);

        if (StringUtils.isNotBlank(bean.getFaceUrlKeys())) {
            String faceUrl = QiniuUtils.getFileAccessUrl(bean.getFaceUrlKeys());
            bean.setFaceUrl(faceUrl);
        }
        model.addAttribute("model", JSON.parse(JSON.toJSONString(bean)));
        return new ModelAndView(RoutesNews.NEWS_DETAIL);
    }

    @RequiresPermissions(value = {"news:add", "news:edit"}, logical = Logical.AND)
    @RequestMapping(value = "/edit/{data}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public Object edit(Model model, HttpServletRequest request, HttpServletResponse response,
                       @PathVariable Boolean data, NewsBean newsBean) {
        ResponseBean responseBean = new ResponseBean();
        if (data) {
            try {
                if (newsBean.getId() == null) {

                    newsBean.setCreateTime(new Date());
                    newsBean.setCreatorId(SubjectManager.getToken().getId());
                    newsService.insertSelective(newsBean);
                } else {
                    newsService.updateByPrimaryKeySelective(newsBean);
                }
            } catch (RuntimeException e) {
                responseBean.error(e.getMessage());
            }
            return responseBean;
        }

        //查询新闻详情
        if (newsBean.getId() != null) {
            NewsBean bean = newsService.selectByPrimaryKey(newsBean.getId());

            if (StringUtils.isNotBlank(bean.getFaceUrlKeys())) {
                String faceUrl = QiniuUtils.getFileAccessUrl(bean.getFaceUrlKeys());
                bean.setFaceUrl(faceUrl);
            }
            model.addAttribute("model", JSON.parse(JSON.toJSONString(bean)));
        }
        //查询新闻频道列表
        List<NewsChannelBean> newsChannelList = newsChannelService.selectAll();
        model.addAttribute("newsChannelList", newsChannelList);
        return new ModelAndView(RoutesNews.NEWS_EDIT);
    }

    @RequiresPermissions("news:delete")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Object delete(Model model, HttpServletRequest request, HttpServletResponse response, String id) {

        ResponseBean responseBean = new ResponseBean();

        try {
            newsService.delete(id);
        } catch (RuntimeException e) {
            responseBean.error(e.getMessage());
        }
        return responseBean;
    }

    /**
     * 接口开关
     * @param model
     * @param request
     * @param response
     * @param type
     * @param switchApi
     * @return
     */
    @RequiresPermissions("news:edit")
    @RequestMapping(value = "/switch/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Object switchApi(Model model,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            @PathVariable int type,
                            boolean switchApi) {

        ResponseBean responseBean = new ResponseBean();
        try {
            if (type == 1) {
                redisUtils.set(RedisKeys.SWITCH_API.key, switchApi);
            } else if (type == 2) {
                redisUtils.set(RedisKeys.SWITCH_API_FILTER.key, switchApi);
            } else if (type == 3) {
                redisUtils.set(RedisKeys.SWITCH_API_AUTO.key, switchApi);
            }
        } catch (RuntimeException e) {
            responseBean.error(e.getMessage());
        }
        return responseBean;
    }

    @RequiresPermissions("news:edit")
    @RequestMapping(value = "/sync", method = RequestMethod.POST)
    @ResponseBody
    public Object syncNews(Model model,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        ResponseBean responseBean = new ResponseBean();

        try {
            newsApiService.syncNews();
        } catch (RuntimeException e) {
            responseBean.error(e.getMessage());
        }
        return responseBean;
    }

}
