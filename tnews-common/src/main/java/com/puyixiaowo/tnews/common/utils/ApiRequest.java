package com.puyixiaowo.tnews.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ApiRequest {

	/**
	 * @param httpUrl
	 *            :请求接口
	 * @param params
	 *            channelId(新闻频道id，必须精确匹配,默认值：5572a109b3cdc86cf39001db)
	 *            channelName(新闻频道名称，可使用%模糊匹配，默认值：国内最新) title(新闻标题，模糊匹配，默认值：上市)
	 *            page(页数，默认1。每页最多20条记录。默认值：1)
	 *            needContent(是否需要返回正文，1为需要，其他为不需要，默认值：0)
	 *            needHtml(是否需要返回正文的html格式，1为需要，其他为不需要，默认值：0)
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String appid, String apikey,
			Map<String, Object> params) throws URISyntaxException {

		params.put("showapi_appid", appid);
		params.put("showapi_sign", apikey);

		Map<String, Object> headers = new HashMap<String, Object>(){{
			put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		}};



		//获取当前链接在指定时间内已请求次数


		return HttpClientUtil.httpGetRequest(httpUrl, headers, params);
	}

	private static String getStringParams(Map<String, Object> params) {
		if (params == null) {
			return "";
		}
		StringBuffer strParams = new StringBuffer();
		for (String key : params.keySet()) {
			String value = "";
			try {
				value = URLEncoder.encode(params.get(key).toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			strParams.append(key + "=" + value + "&");
		}
		return strParams.toString();
	}

	public static void main(String[] args) throws URISyntaxException {
		String re = HttpClientUtil.httpGetRequest("http://route.showapi.com/109-35?needContent=1&maxResult=20&needHtml=1&showapi_appid=24272&showapi_sign=955daa5dd0644ca796aaa978c4c951aa&title=%E6%9C%A8%E6%98%9F");

		System.out.println(re);
	}
}
