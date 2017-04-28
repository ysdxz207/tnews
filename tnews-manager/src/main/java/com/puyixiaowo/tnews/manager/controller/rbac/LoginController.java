/**
 * 
 */
package com.puyixiaowo.tnews.manager.controller.rbac;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Producer;
import com.puyixiaowo.tnews.common.utils.Constants;
import com.puyixiaowo.tnews.manager.bean.UserBean;
import com.puyixiaowo.tnews.manager.controller.BaseController;
import com.puyixiaowo.tnews.manager.shiro.SubjectManager;

/**
 * 
 * @author huangfeihong
 * @date 2017年1月8日 下午6:07:01
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
    Producer captchaProducer;
	
	@RequestMapping("/login")
	public String login() {

		return "login";
	}
	
	@RequestMapping("/doLogin")
	public String doLogin(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			String loginname,
			String password,
			String captcha,
			boolean rememberMe) {
		String code = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isBlank(captcha)) {
			model.addAttribute("message", "请输入验证码");
			return "login";
		}
		
		if (!captcha.equalsIgnoreCase(code)) {
			model.addAttribute("message", "验证码不正确");
			return "login";
		}
		
		if (StringUtils.isBlank(loginname)) {
			model.addAttribute("message", "请输入用户名");
			return "login";
		}
		if (StringUtils.isBlank(password)) {
			model.addAttribute("message", "请输入密码");
			return "login";
		}
		
		try {
			SubjectManager.login(loginname, password, rememberMe);
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return "login";
		}
		UserBean userBean = SubjectManager.getToken();
		model.addAttribute("user", userBean);
		return "redirect:/";
	}
	
	@RequestMapping("/logout")
	public String logout(Model model) {
		try {
			SubjectManager.logout();
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}
		return "redirect:login";
	}
	
	@RequestMapping("/captcha.jpg")
	public void captcha(Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		 HttpSession session = request.getSession();  
	        //String code = (String)session.getAttribute(Constants.KAPTCHA_SESSION_KEY);  
	          
	        response.setDateHeader("Expires", 0);  
	          
	        // Set standard HTTP/1.1 no-cache headers.  
	        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
	          
	        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
	          
	        // Set standard HTTP/1.0 no-cache header.  
	        response.setHeader("Pragma", "no-cache");  
	          
	        // return a jpeg  
	        response.setContentType("image/jpeg");  
	          
	        // create the text for the image  
	        String capText = captchaProducer.createText();  
	          
	        // store the text in the session  
	        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
	          
	        // create the image with the text  
	        ServletOutputStream out = null;  
	        try {
	        	BufferedImage bi = captchaProducer.createImage(capText);  
				out = response.getOutputStream();
				// write the data out  
				ImageIO.write(bi, "jpg", out);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	}
}
