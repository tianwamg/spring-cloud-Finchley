package com.cn.clientcms.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetail implements Serializable {

    private String orderId;

    private Item item;

    public OrderDetail(String orderId, Item item) {
        this.orderId = orderId;
        this.item = item;
    }

    @Override
    public String toString(){
        return "OrderDetail [orderId= "+orderId+", item="+item+"]";
    }
}
