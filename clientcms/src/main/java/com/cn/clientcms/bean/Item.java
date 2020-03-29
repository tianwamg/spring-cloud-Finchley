package com.cn.clientcms.bean;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Item implements Serializable {

    private Long id;

    private String title;

    private String pic;

    private String desc;

    private BigDecimal price;

    public Item(){

    }

    public Item(Long id, String title, String pic, String desc, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.pic = pic;
        this.desc = desc;
        this.price = price;
    }
}
