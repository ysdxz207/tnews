package com.puyixiaowo.tnews.member.service.impl;

import com.puyixiaowo.tnews.bean.Error;
import com.puyixiaowo.tnews.bean.PageRowBounds;
import com.puyixiaowo.tnews.common.utils.RedisUtils;
import com.puyixiaowo.tnews.core.MessageResolver;
import com.puyixiaowo.tnews.enums.FavoriteType;
import com.puyixiaowo.tnews.member.bean.MemberBean;
import com.puyixiaowo.tnews.member.bean.MemberFavoriteBean;
import com.puyixiaowo.tnews.member.persistence.MemberFavoriteCustomMapper;
import com.puyixiaowo.tnews.member.service.MemberFavoriteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("memberFavoriteService")
public class MemberFavoriteServiceImpl implements MemberFavoriteService {
    @Autowired
    private MemberFavoriteCustomMapper memberFavoriteCustomMapper;
    @Autowired
    private RedisUtils redisUtil;
    @Autowired
    private MessageResolver messageResolver;


    @Override
    public long insertSelective(MemberFavoriteBean bean) {

        return memberFavoriteCustomMapper.insertSelective(bean);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {

        return memberFavoriteCustomMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(MemberFavoriteBean bean) {

        return memberFavoriteCustomMapper.updateByPrimaryKeySelective(bean);
    }

    @Override
    public MemberFavoriteBean selectByPrimaryKey(Long id) {

        return memberFavoriteCustomMapper.selectByPrimaryKey(id);
    }

    @Override
    public int selectCountByExample(MemberFavoriteBean memberFavoriteBean) {

        return memberFavoriteCustomMapper.selectCountByExample(buildExample(memberFavoriteBean));
    }

    @Override
    public List<MemberFavoriteBean> selectByExampleAndRowBounds(MemberFavoriteBean memberFavoriteBean, PageRowBounds pageRowBouds) {

        return memberFavoriteCustomMapper.selectByExampleAndRowBounds(buildExample(memberFavoriteBean), pageRowBouds);
    }

    @Override
    public List<MemberFavoriteBean> selectByRowBounds(MemberFavoriteBean newBean, PageRowBounds pageRowBouds) {
        return memberFavoriteCustomMapper.selectByRowBounds(newBean, pageRowBouds);
    }

    @Override
    public int selectCount(MemberFavoriteBean newBean) {
        return memberFavoriteCustomMapper.selectCount(newBean);
    }

    /**
     * @param memberFavoriteBean
     * @return
     */
    private Object buildExample(MemberFavoriteBean memberFavoriteBean) {
        Example example = new Example(MemberFavoriteBean.class);
        Criteria c = example.createCriteria();
        // 参数处理
        if (memberFavoriteBean.getType() != null) {
            c.andEqualTo("type", memberFavoriteBean.getType());
        }
        if (memberFavoriteBean.getMemberId() != null) {
            c.andEqualTo("memberId", memberFavoriteBean.getMemberId());
        }

        example.setOrderByClause(memberFavoriteBean.getOrders());
        return example;
    }

    @Override
    public int delete(String id) {
        if (StringUtils.isBlank(id)) {
            return 0;
        }
        Example example = new Example(MemberFavoriteBean.class);
        Criteria c = example.createCriteria();
        c.andIn("id", Arrays.asList(id.split(",")));
        return memberFavoriteCustomMapper.deleteByExample(example);
    }


    ///////////////////////////////

    /*
     * 用户收藏获取消收藏
     */
    @Override
    public void collectOrCancelFavorite(MemberFavoriteBean memberFavoriteBean) {
        //参数验证
        if (memberFavoriteBean.getType() == null) {
            Error.throwError(MemberFavoriteBean.NO_TYPE, "缺少收藏类型[type]");
        }
        if (memberFavoriteBean.getMemberId() == null) {
            Error.throwError(MemberFavoriteBean.NO_MEMBER_ID, "缺少用户ID[memberId]");
        }
        if (memberFavoriteBean.getFavoriteId() == null) {
            Error.throwError(MemberFavoriteBean.NO_FAVORITE_ID, "缺少收藏ID[favoriteId]");
        }


        //判断用户是否已收藏
        MemberFavoriteBean favorite = memberFavoriteCustomMapper.selectOne(memberFavoriteBean);
        if (favorite != null) {
            memberFavoriteCustomMapper.delete(memberFavoriteBean);
        } else {

            memberFavoriteBean.setCreateTime(new Date());
            memberFavoriteCustomMapper.insertSelective(memberFavoriteBean);
        }
    }

    @Override
    public List<MemberFavoriteBean> getMemberFavoriteList(MemberFavoriteBean memberFavoriteBean, PageRowBounds pageRowBouds) {
        //参数
        if (memberFavoriteBean.getMemberId() == null) {
            Error.throwError(MemberBean.NO_MEMBER_ID, "缺少用户ID[memberId]");
        }
        return selectByExampleAndRowBounds(memberFavoriteBean, pageRowBouds);
    }

}
