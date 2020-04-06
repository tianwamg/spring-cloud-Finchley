package com.cn.dbcenters.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.dbcenters.dao.UserMapper;
import com.cn.dbcenters.model.User;
import com.cn.dbcenters.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "UserServiceImpl")
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    @Cacheable(key="#p0",unless = "#result == null")
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }
}
