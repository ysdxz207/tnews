/**
 *
 */
package com.puyixiaowo.tnews.web.action.member;

import com.puyixiaowo.tnews.bean.RequestBean;
import com.puyixiaowo.tnews.bean.ResponseBean;
import com.puyixiaowo.tnews.bean.VerificationCode;
import com.puyixiaowo.tnews.common.utils.Constants;
import com.puyixiaowo.tnews.member.bean.MemberBean;
import com.puyixiaowo.tnews.member.service.MemberService;
import com.puyixiaowo.tnews.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户
 *
 * @author huangfeihong
 * @date 2017年2月24日
 */
@Controller("memberAction")
public class MemberAction extends BaseAction {

    @Autowired
    private MemberService memberService;
    @Autowired
    private HttpSession session;

    /*
     * 发送短信验证码，1注册，2忘记密码
     */
    public String sendVerificationCode(HttpServletRequest request,
                                       HttpServletResponse response, RequestBean requestBean) {

        ResponseBean responseBean = new ResponseBean();
        try {
            memberService.sendVerificationCode(requestBean.toEntity(VerificationCode.class));
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    /*
     * 用户注册
     */
    public String registerMember(HttpServletRequest request,
                                 HttpServletResponse response, RequestBean requestBean) {
        ResponseBean responseBean = new ResponseBean();

        try {
            memberService.insertRegisterMember(requestBean.toEntity(MemberBean.class));
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

    /*
     * 用户登录
     */
    public String loginMember(HttpServletRequest request,
                              HttpServletResponse response, RequestBean requestBean) {

        ResponseBean responseBean = new ResponseBean();
        try {
            MemberBean memberBean = memberService.selectLoginMember(requestBean.toEntity(MemberBean.class));
            session.setAttribute(Constants.MEMBER_SESSION_KEY, memberBean);
            responseBean.setData(memberBean);
        } catch (Exception e) {
            responseBean.error(e);
        }
        return responseBean.serialize();
    }

}
