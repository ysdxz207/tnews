package com.puyixiaowo.tnews.member.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.puyixiaowo.tnews.bean.BaseBean;
import com.puyixiaowo.tnews.common.utils.CustomDateTimeSerializer;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "t_member_favorite")
public class MemberFavoriteBean extends BaseBean implements Serializable {

    @Id
    private Long id;
    private Long memberId;
    private Integer type;
    private Long favoriteId;
    @JSONField(serializeUsing = CustomDateTimeSerializer.class)
    private Date createTime;
    private static final long serialVersionUID = 1L;



    /////////////////////


    /////////////////////
    public static final String NO_TYPE = "NO_TYPE";
    public static final String NO_MEMBER_ID = "NO_MEMBER_ID";
    public static final String NO_FAVORITE_ID = "NO_FAVORITE_ID";
    public static final String NOT_EXISTS = "NOT_EXISTS";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}