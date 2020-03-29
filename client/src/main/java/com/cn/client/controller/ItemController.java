package com.cn.client.controller;

import com.cn.client.bean.Item;
import com.cn.client.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "item/{id}")
    public Item queryById(@PathVariable("id") Long id){
        return itemService.queryById(id);
    }
}
