package com.puyixiaowo.tnews.member.service;

import com.puyixiaowo.tnews.bean.PageRowBounds;
import com.puyixiaowo.tnews.member.bean.MemberFavoriteBean;

import java.util.List;

/**
 * 用户收藏服务接口
 *
 * @author huangfeihong
 * @date 2017年03月05日
 */
public interface MemberFavoriteService {

    long insertSelective(MemberFavoriteBean memberFavoriteBean);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberFavoriteBean memberFavoriteBean);

    MemberFavoriteBean selectByPrimaryKey(Long id);

    List<MemberFavoriteBean> selectByExampleAndRowBounds(MemberFavoriteBean memberFavoriteBean, PageRowBounds pageRowBouds);

    int selectCountByExample(MemberFavoriteBean memberFavoriteBean);

    List<MemberFavoriteBean> selectByRowBounds(MemberFavoriteBean memberFavoriteBean, PageRowBounds pageRowBouds);

    int selectCount(MemberFavoriteBean memberFavoriteBean);

    int delete(String id);

    void collectOrCancelFavorite(MemberFavoriteBean memberFavoriteBean);

    List<MemberFavoriteBean> getMemberFavoriteList(MemberFavoriteBean memberFavoriteBean, PageRowBounds pageRowBouds);
}
