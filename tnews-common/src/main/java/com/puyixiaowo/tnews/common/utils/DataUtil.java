package com.puyixiaowo.tnews.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class DataUtil {

	public static <T> T parse(String content, Class<T> clazz) throws Exception {
        if (StringUtils.isEmpty(content)) {
            content = "{}";
        }

        try {
            return JSON.parseObject(content, clazz);
        } catch (Throwable e) {
            throw new Exception("JSON解析错误, " + e.getMessage());
        }
    }

    public static Map<String, Object> parse(String content) {
        if (StringUtils.isEmpty(content)) {
            content = "{}";
        }

        try {
            return JSON.parseObject(content);
        } catch (Throwable e) {
            throw new RuntimeException("JSON解析错误, " + e.getMessage());
        }
    }
	
	
	
    /**
     * @description: 获取GET请求传入的参数
     * @param request
     *            ，content
     * @return:String
     */
    public static String getNoKeyParamValue(HttpServletRequest request, String content) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Iterator<Entry<String, String[]>> iterator = parameterMap.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, String[]> entry = iterator.next();
            // System.out.println(entry.getKey()+entry.getValue()[0]);
            if (entry.getValue().length == 1 && "".equalsIgnoreCase(entry.getValue()[0])) {
                content = entry.getKey();
                break;
            }
            if (!entry.getKey().endsWith("}") && !"".equalsIgnoreCase(entry.getValue()[0])
                    && entry.getValue()[0].startsWith("\"")) {// 处理{"op":"="}串
                content = entry.getKey() + "=" + entry.getValue()[0];
                break;
            }
        }
        return content;
    }



    /**
     * 去除php请求报文尾部php程序加上的=号
     * 
     * @param content
     * @return
     */
    public static String clearEq(String content) {
        if (content.endsWith("=")) {
            content = content.substring(0, content.length() - 1);
        }
        return content;
    }



    public static String inputStream2String(InputStream inputStream) throws IOException {
        byte[] buff = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int count;
        while ((count = inputStream.read(buff)) != -1) {
            baos.write(buff, 0, count);
        }
        String result = new String(baos.toByteArray(), "UTF-8");
        baos.close();
        return result;
    }

}
