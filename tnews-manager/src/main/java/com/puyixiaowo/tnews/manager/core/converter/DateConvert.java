package com.puyixiaowo.tnews.manager.core.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 全局日期处理类
 * Convert<T,S>
 *         泛型T:代表客户端提交的参数 String
 *         泛型S:通过convert转换的类型
   
 */
public class DateConvert implements Converter<String, Date> {

    @Override
    public Date convert(String stringDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
        	if (StringUtils.isNotBlank(stringDate)) {
        		return simpleDateFormat.parse(stringDate);
        	}
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}