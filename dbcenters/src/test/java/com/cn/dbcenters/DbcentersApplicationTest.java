package com.cn.dbcenters;

import com.cn.dbcenters.model.User;
import com.cn.dbcenters.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DbcentersApplicationTest {

    @Autowired
    UserService userService;

    @Test
    public void test(){
        /*User user = userService.selectById(1);
        System.out.print(user.toString());*/
        System.out.print(123);
    }
}
