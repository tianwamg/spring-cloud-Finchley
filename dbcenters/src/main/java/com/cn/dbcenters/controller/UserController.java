package com.cn.dbcenters.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cn.dbcenters.model.User;
import com.cn.dbcenters.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/test")
    public String test(){
        /*User user = userService.getById(1);
        System.out.print(user.toString());
        Integer count = userService.count();
        System.out.print(count);*/
        User user = userService.selectById(5);
        //List<User> list = userService.list();
        //list.forEach(System.out::println);
        return user.toString();

    }

    @GetMapping(value = "/findall",produces = "application/json;charset=utf-8")
    public IPage<User> findAll(Integer pageNum,Integer pageSize){
        IPage<User> iPage = userService.findAll(pageNum,pageSize);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",iPage);
        jsonObject.put("code","200");
        return iPage;
    }
}
