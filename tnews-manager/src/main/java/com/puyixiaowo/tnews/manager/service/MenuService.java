/**
 *
 */
package com.puyixiaowo.tnews.manager.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.puyixiaowo.tnews.manager.bean.MenuBean;
import com.puyixiaowo.tnews.manager.bean.other.MenuPermissionBean;
import com.puyixiaowo.tnews.manager.persistence.MenuCustomMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangfeihong
 * @date 2017年1月8日 下午5:53:00
 */
@Service
public class MenuService {
    @Autowired
    private MenuCustomMapper menuCustomMapper;

    /*
     * 添加
     */
    public int insertSelective(MenuBean menuBean) throws RuntimeException {
        return menuCustomMapper.insertSelective(menuBean);
    }

    /*
     * 修改
     */
    public int updateByPrimaryKeySelective(MenuBean menuBean)
            throws RuntimeException {
        return menuCustomMapper.updateByPrimaryKeySelective(menuBean);
    }

    /*
     * 删除
     */
    public void delete(String id) throws RuntimeException {
        if (StringUtils.isBlank(id)) {
            return;
        }
        Example example = new Example(MenuBean.class);
        Example.Criteria c = example.createCriteria();
        c.andIn("id", Arrays.asList(id.split(",")));
        menuCustomMapper.deleteByExample(example);
    }

    /*
     * 根据ID查询信息
     */
    public MenuBean selectByPrimaryKey(Long id) {
        return menuCustomMapper.selectByPrimaryKey(id);
    }

    /*
     * 分页查询
     */
    public List<MenuBean> selectByRowBounds(MenuBean menuBean,
                                            RowBounds rowBounds) {

        return menuCustomMapper.selectByRowBounds(menuBean, rowBounds);
    }

    /*
     * 分页查询
     */
    public List<MenuBean> selectByExampleAndRowBounds(Example example,
                                                      RowBounds rowBounds) {

        return menuCustomMapper.selectByExampleAndRowBounds(example, rowBounds);
    }

    /*
     * 查询总记录数
     */
    public int selectCount(MenuBean menuBean) {
        return menuCustomMapper.selectCount(menuBean);
    }

    /*
     * 查询总记录数
     */
    public int selectCountByExample(Example example) {
        return menuCustomMapper.selectCountByExample(example);
    }


    /*
     * 查询所有
     */
    public List<MenuBean> selectAll() {
        return menuCustomMapper.selectAll();
    }

    /////////////////////////

    public List<MenuBean> selectNestedByType(int type) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        return menuCustomMapper.selectNestedByType(params);
    }

    /*
     * 添加或修改
     */
    public void insertOrUpdateSelective(String json) throws RuntimeException {
        if (StringUtils.isBlank(json)) {
            return;
        }
        JSONArray arr = JSON.parseArray(json);
        for (int i = 0; i < arr.size(); i++) {
            MenuBean menuBean = arr.getJSONObject(i).toJavaObject(MenuBean.class);
            if (menuBean.getId() == null) {
                menuCustomMapper.insertSelective(menuBean);
            } else {
                menuCustomMapper.updateByPrimaryKeySelective(menuBean);
            }
        }
    }

    /*
     *
     */
    public List<MenuPermissionBean> selectValidMenuPermissions(Long roleId) {
        return menuCustomMapper.selectValidMenuPermissions(roleId);
    }
}
