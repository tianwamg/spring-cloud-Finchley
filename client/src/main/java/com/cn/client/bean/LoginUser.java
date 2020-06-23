package com.cn.client.bean;


import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUser implements Serializable {

    private Integer id;
    private Integer roleId;
    private String username;
}
