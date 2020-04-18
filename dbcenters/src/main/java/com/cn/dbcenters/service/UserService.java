package com.cn.dbcenters.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cn.dbcenters.model.User;

public interface UserService extends IService<User> {

    public User selectById(Integer id);

    public IPage<User> findAll(Integer pageNum, Integer pageSize);
}
