package com.cn.dbcenters.model;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("nan_user")
public class User extends Model {

    @TableId("id")
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("phone")
    private String phone;

    @TableField("password")
    private String password;

    @TableField("gender")
    private short gender;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField("status")
    @TableLogic
    private short status;

    @TableField("role")
    private Integer role;

    @TableField("salt")
    private String salt;

    @TableField("ext1")
    private String ext1;

    @TableField("ext2")
    private String ext2;

    @TableField("ext3")
    private String ext3;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
