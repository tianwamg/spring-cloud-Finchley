package com.cn.clientcms.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class Order implements Serializable {

    private String orderId;

    private Long userId;

    private Date createTime;

    private Date updateTime;

    private List<OrderDetail> list;

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", userId=" + userId
                + ", createDate=" + createTime + ", updateDate=" + updateTime
                + "]";
    }
}
