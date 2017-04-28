package com.puyixiaowo.tnews.member.service.impl;

import com.puyixiaowo.tnews.bean.Error;
import com.puyixiaowo.tnews.bean.VerificationCode;
import com.puyixiaowo.tnews.common.utils.EncoderUtil;
import com.puyixiaowo.tnews.common.utils.RandomUtil;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.common.utils.SmsUtil;
import com.puyixiaowo.tnews.core.MessageResolver;
import com.puyixiaowo.tnews.enums.RedisKeys;
import com.puyixiaowo.tnews.member.bean.MemberBean;
import com.puyixiaowo.tnews.member.persistence.MemberCustomMapper;
import com.puyixiaowo.tnews.member.service.MemberService;
import com.taobao.api.ApiException;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberCustomMapper memberCustomMapper;
    @Autowired
    private RedisUtils redisUtil;
    @Autowired
    private MessageResolver messageResolver;


    @Override
    public long insertSelective(MemberBean bean) {

        return memberCustomMapper.insertSelective(bean);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {

        return memberCustomMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberBean bean) {

        return memberCustomMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public MemberBean selectByPrimaryKey(Long id) {

        return memberCustomMapper.selectByPrimaryKey(id);
    }

    @Override
    public int selectCountByExample(MemberBean memberBean) {

        return memberCustomMapper.selectCountByExample(buildExample(memberBean));
    }

    @Override
    public List<MemberBean> selectByExampleAndRowBounds(MemberBean memberBean, RowBounds rowBounds) {

        return memberCustomMapper.selectByExampleAndRowBounds(buildExample(memberBean), rowBounds);
    }

    @Override
    public List<MemberBean> selectByRowBounds(MemberBean newBean, RowBounds rowBounds) {
        return memberCustomMapper.selectByRowBounds(newBean, rowBounds);
    }

    @Override
    public int selectCount(MemberBean newBean) {
        return memberCustomMapper.selectCount(newBean);
    }

    /**
     * @param memberBean
     * @return
     */
    private Object buildExample(MemberBean memberBean) {
        Example example = new Example(MemberBean.class);
        Criteria c = example.createCriteria();
        // 参数处理
        if (StringUtils.isNotBlank(memberBean.getProvince())) {
            c.andLike("province", "%" + memberBean.getProvince() + "%");
        }
        if (StringUtils.isNotBlank(memberBean.getCity())) {
            c.andLike("city", "%" + memberBean.getCity() + "%");
        }
        if (memberBean.getCredits() != null) {
            c.andEqualTo("credits", memberBean.getCredits());
        }
        if (StringUtils.isNotBlank(memberBean.getEmail())) {
            c.andEqualTo("email", memberBean.getEmail());
        }
        if (StringUtils.isNotBlank(memberBean.getMobile())) {
            c.andLike("mobile", "%" + memberBean.getMobile() + "%");
        }
        if (StringUtils.isBlank(memberBean.getNickname())) {
            c.andLike("nickname", "%" + memberBean.getMobile() + "%");
        }
        if (StringUtils.isNotBlank(memberBean.getUsername())) {
            c.andLike("username", "%" + memberBean.getUsername() + "%");
        }
        if (memberBean.getSex() != null) {
            c.andEqualTo("sex", memberBean.getSex());
        }
        if (memberBean.getVip() != null) {
            c.andEqualTo("vip", memberBean.getVip());
        }
        if (memberBean.getStatus() != null) {
            c.andEqualTo("status", memberBean.getStatus());
        }

        example.setOrderByClause(memberBean.getOrders());
        return example;
    }

    @Override
    public int delete(String id) {
        if (StringUtils.isBlank(id)) {
            return 0;
        }
        Example example = new Example(MemberBean.class);
        Criteria c = example.createCriteria();
        c.andIn("id", Arrays.asList(id.split(",")));
        return memberCustomMapper.deleteByExample(example);
    }


    ///////////////////////////////

    /**
     * 发送验证码
     *
     * @throws ApiException
     */
    @Override
    public void sendVerificationCode(VerificationCode verificationCode) {

        int type = 1;//短信类型
        String mobile = null;//手机号码
        String key = "";//存入redis key
        String code = RandomUtil.generateRandomSix();//生成验证码
        String minuteStr = messageResolver.getMessage("tnews.sms.minute");//获取短信有效期
        minuteStr = StringUtils.isBlank(minuteStr) ? "10" : minuteStr;
        int minute = Integer.valueOf(minuteStr);//有效期,分钟
        String templateCode = messageResolver.getMessage("member.reg.template_code");//短信模板代码

        if (StringUtils.isBlank(verificationCode.getMobile())) {
            Error.throwError(VerificationCode.NO_MOBILE, "没有传入手机号码[mobile]");
        }

        if (verificationCode.getType() == null) {
            Error.throwError(VerificationCode.NO_TYPE, "没有传入验证码类型[type]");
        }
        mobile = verificationCode.getMobile();
        // send verification code
        switch (type) {
            case 1:

                key = RedisKeys.SMS_MEMBER_REG.key + mobile;
                break;
            case 2:
                key = RedisKeys.SMS_MEMBER_MODIFY_PASS.key + mobile;
                templateCode = messageResolver.getMessage("member.modify_pass.template_code");
                break;

            default:
                key = RedisKeys.SMS_MEMBER_REG.key + mobile;
                break;
        }

        try {
            SmsUtil.send(mobile, templateCode, "{'code':'" + code + "'}");
        } catch (ApiException e) {
            Error.throwError(VerificationCode.SEND_SMS_ERROR, e.getMessage());
        }

        // put in redis
        boolean flag = redisUtil.set(key, code, minute * 60L);// 有效时间
        if (!flag) {
            Error.throwError(VerificationCode.SEND_SMS_ERROR, "push verification code to redis failed!redis:key=" + key + ",code=" + code + "minute=" + minute);
        }
        System.out.println("redis:key=" + key + ",code=" + code);
    }

    @Override
    public long insertRegisterMember(MemberBean memberBean) throws RuntimeException {

        if (StringUtils.isBlank(memberBean.getUsername())) {
            Error.throwError(MemberBean.NO_USERNAME, "没有传入用户名[username]");
        }
        if (StringUtils.isBlank(memberBean.getPassword())) {
            Error.throwError(MemberBean.NO_PASSWORD, "没有传入密码[password]");
        }
        if (StringUtils.isBlank(memberBean.getVerificationCode())) {
            Error.throwError(MemberBean.NO_VERIFICATION_CODE, "没有传入验证码[verificationCode]");
        }
        if (StringUtils.isBlank(memberBean.getProvince())) {
            Error.throwError(MemberBean.NO_PROVINCE, "没有传入所在省[province]");
        }
        if (StringUtils.isBlank(memberBean.getCity())) {
            Error.throwError(MemberBean.NO_CITY, "没有传入所在市[city]");
        }

        // 校验验证码
        String code = memberBean.getVerificationCode();
        String mobile = memberBean.getUsername();
        memberBean.setMobile(mobile);

        Object obj = redisUtil.get(RedisKeys.SMS_MEMBER_REG.key + mobile);

        if (ObjectUtils.isEmpty(obj)) {
            Error.throwError(VerificationCode.EXPIRE_VERIFICATION_CODE, "验证码已失效");
        }

        if (!code.equals(obj.toString())) {
            Error.throwError(VerificationCode.WRONG_VERIFICATION_CODE, "验证码错误");
        }
        Example example = new Example(MemberBean.class);
        Criteria c = example.createCriteria();
        c.andEqualTo("mobile", memberBean.getMobile());
        if (memberCustomMapper.selectCountByExample(example) > 0) {
            Error.throwError(MemberBean.EXISTS_USERNAME, "该手机已注册");
        }

        memberBean.setHeadImg("");// 默认头像
        memberBean.setRegTime(new Date());
        memberBean.setPassword(EncoderUtil.encodePass(memberBean.getPassword()));
        memberBean.setStatus(1);//新用户为有效用户

        return memberCustomMapper.insertSelective(memberBean);
    }

    @Override
    public MemberBean selectLoginMember(MemberBean memberBean) throws RuntimeException {
        //参数验证
        if (StringUtils.isBlank(memberBean.getUsername())) {
            Error.throwError(MemberBean.NO_USERNAME, "没有传入用户名[username]");
        }
        if (StringUtils.isBlank(memberBean.getPassword())) {
            Error.throwError(MemberBean.NO_PASSWORD, "没有传入密码[password]");
        }

        memberBean.setPassword(EncoderUtil.encodePass(memberBean.getPassword()));
        memberBean = memberCustomMapper.selectOne(memberBean);
        if (memberBean == null) {
            Error.throwError(MemberBean.LOGIN_FAILED, "登录失败");
        }
        memberBean.setPassword(null);
        return memberBean;
    }


}
