package com.puyixiaowo.tnews.manager.controller.tnews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.manager.enums.ImageTag;
import com.puyixiaowo.tnews.manager.utils.QiniuUtils;

/**
 * 上传
 * @author huangfeihong
 * @date 2017年1月26日 下午4:04:08
 */
@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController{
	
	
	@RequestMapping("/images")
	@ResponseBody
	public Object uploadImages(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			MultipartFile file,
			Long size,
			String name,
			String type,
			ImageTag tag) {
		
		ResponseBean responseBean = new ResponseBean();
		String key = QiniuUtils.generateImageKey(tag);
		
		String fileName = file.getOriginalFilename();
		String extName = fileName.substring(fileName.indexOf("."));
		key += extName;
		try {
			QiniuUtils.upload(file, key);
			JSONObject json = new JSONObject();
			json.put("key", key);
			json.put("url", QiniuUtils.getFileAccessUrl(key));
			responseBean.setData(json);
		} catch (Exception e) {
			responseBean.error(e.getMessage());
		}
		return responseBean.serialize();
	}
	
}
