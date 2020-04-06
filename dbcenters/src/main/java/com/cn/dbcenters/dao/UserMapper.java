package com.cn.dbcenters.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.dbcenters.model.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户mapper接口
 */
public interface UserMapper extends BaseMapper<User> {

    public User selectById(@Param("id") Integer id);
}
