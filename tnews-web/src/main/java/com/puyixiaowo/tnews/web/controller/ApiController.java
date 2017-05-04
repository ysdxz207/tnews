/**
 *
 */
package com.puyixiaowo.tnews.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.puyixiaowo.tnews.bean.RequestBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.common.utils.DataUtil;
import com.puyixiaowo.tnews.web.action.BaseAction;
import com.puyixiaowo.tnews.web.util.ApplicationContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

/**
 * @author huangfeihong
 * @date 2016年12月6日 下午9:18:09
 */
@Controller
public class ApiController extends BaseAction {

    @RequestMapping("/api")
    @ResponseBody
    public Object getAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestBean requestBean = null;

        // 获取请求方式
        String methodType = request.getMethod();
        HttpMethod method = HttpMethod.valueOf(methodType);

        String content = "";
        String decodeContent = "";
        // 根据不同的请求类型 进行字符串解析
        if (HttpMethod.GET.equals(method)) {
            content = DataUtil.getNoKeyParamValue(request, content);
        } else {
            content = DataUtil.inputStream2String(request.getInputStream());
        }
        //System.out.println(content);
        // 对特殊字符进行编码操作
        content = content.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        content = content.replaceAll("\\+", "%2B");
        // 进行解码
        try {
            content = URLDecoder.decode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // LogUtil.error(e.getMessage());
        }

        decodeContent = content;

        if (StringUtils.isEmpty(content)) {
            ResponseBean rb = new ResponseBean();
            rb.errorMessage("请求报文格式不正确，包含特殊字符,get请求请做URL编码！");
            return JSONObject.toJSONString(rb);
        }
        // 封装json报文为实体
        try {
            requestBean = DataUtil.parse(decodeContent, RequestBean.class);
        } catch (Exception e) {
            return new ResponseBean().errorMessage(e.getMessage());
        }
        // 检查是否有参数
        if (null == requestBean) {
            // LogUtil.error("请求参数不能为空!");
            return JSONObject.toJSONString(
                    new ResponseBean().errorMessage("请求参数不能为空"));
        }

        //
        // if (HttpMethod.GET.matches(request.getMethod())) {
        //
        // } else if (HttpMethod.POST.matches(request.getMethod())) {
        //
        // }

        BaseAction baseController = ApplicationContextUtil.getBean(requestBean.getAction(), BaseAction.class);

        if (baseController == null) {
            return new ResponseBean().errorMessage("can not find action:" + requestBean.getAction()).serialize();
        }

        Method m = null;
        Object result = "";

        try {
            m = baseController.getClass().getDeclaredMethod(requestBean.getMethod(), HttpServletRequest.class,
                    HttpServletResponse.class, RequestBean.class);
            result = m.invoke(baseController, request, response, requestBean);
        } catch (Exception e) {
            return new ResponseBean().errorMessage(e.getMessage()).serialize();
        }

        return result;
    }
}
