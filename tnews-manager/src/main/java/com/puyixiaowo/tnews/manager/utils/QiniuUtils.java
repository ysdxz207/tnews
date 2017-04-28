package com.puyixiaowo.tnews.manager.utils;

import com.aliyun.odps.utils.StringUtils;
import com.puyixiaowo.tnews.common.utils.DateUtils;
import com.puyixiaowo.tnews.manager.enums.ImageTag;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * 
 * @author huangfeihong
 * @date 2017年1月26日 下午7:41:44
 */
public class QiniuUtils {
	private static final String ACCESS_KEY = "8N8OzzdKz2gZ_kNyHEShz8XOmCXi4UCNHwplFMmU";
	private static final String SECRET_KEY = "5jWHCnZxDm6Ot9qBMcRxjEnTSeXo9FTq8Q9ZVh3G";
	// 默认上传空间
	private static final String BUCKET_NAME = "puyixiaowo-tnews";
	// 空间默认域名
	private static final String BUCKET_HOST_NAME = "http://okdwc9ap3.bkt.clouddn.com";

	private static Zone zone = Zone.autoZone();

	private static Configuration config = new Configuration(zone);
	private static UploadManager uploadManager = new UploadManager(config);

	private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	/**
	 * 普通上传
	 * @param fileFullPath
	 * @param key
	 * 			是否覆盖文件
	 * @return
	 */
	public static String upload(String fileFullPath, String key) {
		String result = "";
		try {
			String token = null;
			
			if (StringUtils.isBlank(key) && fileFullPath.indexOf(".") > 0) {
				key = FilenameUtils.getName(fileFullPath);
			} else {
				throw new RuntimeException("Illegal fileFullPath:" + fileFullPath);
			}
			
			//覆盖,token有效期4小时
			token = auth.uploadToken(BUCKET_NAME, key, 3600 * 4, new StringMap());
			
			// 调用put方法上传
			Response res = uploadManager.put(fileFullPath, key, token);
			result = res.bodyString();
		} catch (QiniuException e) {
			Response r = e.response;
			try {
				throw new RuntimeException(r.bodyString());
			} catch (QiniuException e1) {
				e1.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param file
	 * @param key
	 * @return
	 */
	public static String upload(MultipartFile file, String key){
		String result = "";
		
		try {
			String token = null;
			
			if (StringUtils.isBlank(key) && !file.isEmpty()) {
				key = file.getOriginalFilename();
			}
			
			//覆盖,token有效期4小时
			token = auth.uploadToken(BUCKET_NAME, key, 3600 * 4, new StringMap());
			
			// 调用put方法上传
			Response res = uploadManager.put(file.getInputStream(), key, token, new StringMap(), file.getContentType());
			result = res.bodyString();
		} catch (QiniuException e) {
			Response r = e.response;
			try {
				throw new RuntimeException(r.bodyString());
			} catch (QiniuException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e){
			throw new RuntimeException(e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static FileInfo getFileInfo(String key) throws RuntimeException{
		// 实例化一个BucketManager对象
		BucketManager bucketManager = new BucketManager(auth, config);
		// 调用stat()方法获取文件的信息
		FileInfo info = null;
		try {
			info = bucketManager.stat(BUCKET_NAME, key);
		} catch (QiniuException e) {
			throw new RuntimeException(e.getMessage());
		}
		return info;
	}
	/**
	 * 获取访问链接
	 * @param key
	 * @return
	 */
	public static String getFileAccessUrl(String key){
		return auth.privateDownloadUrl(BUCKET_HOST_NAME + "/" + key);
	}
	/**
	 * 生成key
	 * @return
	 */
	public static String generateImageKey(ImageTag tag){
		return tag + "_" + DateUtils.formatNumDateTime(new Date());
	}
	
	
	
	public static void main(String[] args) {
		String str = QiniuUtils.upload("D:/workspace/57570580_p0.jpg", null);
		System.out.println(str);
//		FileInfo info = getFileInfo("57570580_p0.jpg");
//		System.out.println(info.key);
//		System.out.println(info.hash);
		
		//System.out.println(getFileAccessUrl("57570580_p0.jpg"));
		
		//System.out.println(generateImageKey(ImageTag.NEWS_FACE_PIC));
	}
}