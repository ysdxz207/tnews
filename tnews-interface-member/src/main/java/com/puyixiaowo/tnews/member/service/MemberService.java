package com.puyixiaowo.tnews.member.service;

import com.puyixiaowo.tnews.bean.VerificationCode;
import com.puyixiaowo.tnews.member.bean.MemberBean;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author huangfeihong
 * @date 2017年2月24日
 */
public interface MemberService {

    long insertSelective(MemberBean memberBean);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberBean memberBean);

    MemberBean selectByPrimaryKey(Long id);

    List<MemberBean> selectByExampleAndRowBounds(MemberBean memberBean, RowBounds rowBouds);

    int selectCountByExample(MemberBean memberBean);

    List<MemberBean> selectByRowBounds(MemberBean memberBean, RowBounds rowBounds);

    int selectCount(MemberBean memberBean);

    int delete(String id);

    void sendVerificationCode(VerificationCode verificationCode) throws RuntimeException;

    long insertRegisterMember(MemberBean memberBean) throws RuntimeException;

    MemberBean selectLoginMember(MemberBean memberBean) throws RuntimeException;
}
