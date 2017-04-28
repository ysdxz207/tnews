package com.puyixiaowo.tnews.common.utils;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;

/**
 * 阿里大于发送短信工具
 * @author huangfeihong
 * @date 2017年2月24日
 */
public class SmsUtil {
	private final static String url="http://gw.api.taobao.com/router/rest";
	private final static String appkey="23653018";
	private final static String secret="8ab1387ce04deabf6fb11e049e9c5523";
	private final static String FreeSignName="推他新闻";
	
	public static void send(String FreeSignName,String RecNum,String TemplateCode,String ParamString,String extend) throws ApiException{
		
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend( extend );
		req.setSmsType( "normal" );
		req.setSmsFreeSignName(FreeSignName);
		req.setSmsParamString( ParamString );
		req.setRecNum( RecNum );
		req.setSmsTemplateCode(TemplateCode );
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		client.execute(req);
//		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
//		System.out.println(rsp.getBody());
	}
	
	public static void send(String FreeSignName,String RecNum,String TemplateCode,String ParamString) throws ApiException{
		send(FreeSignName, RecNum, TemplateCode, ParamString,"");
	}
	
	public static void send(String RecNum,String TemplateCode,String ParamString) throws ApiException{
		send(FreeSignName, RecNum, TemplateCode, ParamString,"");
	}
	
	public static void main(String[] args) {
		String FreeSignName="推他新闻";
		String RecNum="18502072157";
		String TemplateCode="SMS_49320007";
		String ParamString="{'code':'654321'}";
		try {
			SmsUtil.send(FreeSignName, RecNum, TemplateCode, ParamString);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
