package com.cn.clientcms.service;

import com.cn.clientcms.bean.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class ItemService {

    @Autowired
    private RestTemplate restTemplate;

    public Item queryById(Long id){
        return this.restTemplate.getForObject("http://127.0.0.1:8762/item/"+id,Item.class);
    }
}
