package com.cn.clientcms.service;

import com.cn.clientcms.bean.Item;
import com.cn.clientcms.bean.Order;
import com.cn.clientcms.bean.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class OrderService {

    private static final Map<String,Order> map = new HashMap<>();

    static{
        Order order = new Order();
        order.setOrderId("20200324");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setUserId(1l);
        List<OrderDetail> list = new ArrayList<>();
        Item item = new Item();
        item.setId(1l);
        list.add(new OrderDetail(order.getOrderId(),item));

        item = new Item();
        item.setId(2l);
        list.add(new OrderDetail(order.getOrderId(),item));

        order.setList(list);
        map.put(order.getOrderId(),order);
    }

    @Autowired
    private ItemService itemService;

    public Order queryOrderById(String orderId){
        Order order = map.get(orderId);
        if(order == null){
            return null;
        }
        List<OrderDetail> list = order.getList();
        for (OrderDetail detail:list){
            Item item = this.itemService.queryById(detail.getItem().getId());
            if(item == null){
                continue;
            }
            detail.setItem(item);
        }
        return order;
    }
}
