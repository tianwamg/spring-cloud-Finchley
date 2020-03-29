package com.cn.dbcenters.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cn.dbcenters.dao.UserMapper;
import com.cn.dbcenters.model.User;
import com.cn.dbcenters.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
}
