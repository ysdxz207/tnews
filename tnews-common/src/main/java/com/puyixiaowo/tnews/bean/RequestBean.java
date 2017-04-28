/**
 *
 */
package com.puyixiaowo.tnews.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangfeihong
 * @date 2016年12月6日 下午9:13:33
 */
public class RequestBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String action;//所要请求的action
    private String method;//请求的方法
    private String data;//参数

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /************************/
    /*
	 * 转为map参数
	 */
    public Map<String, Object> toParam() throws Exception {
        if (StringUtils.isBlank(this.data) || JSONObject.parseObject(this.data).isEmpty()) {
            return new HashMap<String, Object>();
        }
        JSONObject jsonObj = JSONObject.parseObject(this.data);
        Map<String, Object> map = new HashMap<String, Object>();
        for (String key : jsonObj.keySet()) {
            String str = jsonObj.getString(key);
            if (StringUtils.isBlank(str)) {
                Error.throwError(key + "的值为空");
            }
            map.put(key, str);
        }
        return map;
    }

    /*
     * 转为实体
     */
    public <T> T toEntity(Class<T> clazz) throws Exception {
        if (StringUtils.isBlank(this.data) || JSONObject.parseObject(this.data).isEmpty()) {
            Error.throwError("data值为空");
        }
        T t = null;
        try {
            t = JSON.toJavaObject(JSONObject.parseObject(this.data), clazz);
        } catch (Exception e) {
            Error.throwError(e.getMessage());
        }
        return t;
    }

    /*
     * 获取long类型
     */
    public Long getLong(String key) throws Exception {
        if (StringUtils.isBlank(this.data) || JSONObject.parseObject(this.data).isEmpty()) {
            Error.throwError("data值为空");
        }
        String str = JSONObject.parseObject(this.data).getString(key);
        if (StringUtils.isBlank(str)) {
            Error.throwError(key + "的值为空");
        }
        Long result = null;
        try {
            result = TypeUtils.castToLong(str);
        } catch (Exception e) {
            Error.throwError(str + "不是数字类型");
        }
        return result;
    }

    /*
     * 获取String类型
     */
    public String getStr(String key) throws Exception {
        if (StringUtils.isBlank(this.data) || JSONObject.parseObject(this.data).isEmpty()) {
            Error.throwError("data值为空");
        }
        String str = JSONObject.parseObject(this.data).getString(key);
        if (StringUtils.isBlank(str)) {
            Error.throwError(key + "的值为空");
        }

        return str;
    }

    /*
     * 转为pageBean
     */
    public PageBean toPageBean() {
        PageBean pageBean = null;
        try {
            pageBean = this.toEntity(PageBean.class);
        } catch (Exception e) {
            Error.throwError("request bean to page bean error");
        }
        return new PageBean(pageBean.getPageCurrent(), pageBean.getPageSize());
    }

    public static void main(String[] args) throws Exception {
        RequestBean bean = new RequestBean();
        bean.setAction("goodsAction");
        bean.setData("{\"id\":\"1\"}");
        bean.setMethod("get");
        Map<String, Object> map = bean.toParam();
        for (String key : map.keySet()) {
            System.out.println("key=" + key);
            System.out.println("value=" + map.get(key));
        }
    }

}
