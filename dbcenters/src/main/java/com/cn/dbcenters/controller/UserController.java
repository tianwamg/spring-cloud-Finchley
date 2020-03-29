package com.cn.dbcenters.controller;

import com.cn.dbcenters.model.User;
import com.cn.dbcenters.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/test")
    public String test(){
        User user = userService.getById(1);
        System.out.print(user.toString());
        Integer count = userService.count();
        System.out.print(count);
        return user.toString();
    }
}
