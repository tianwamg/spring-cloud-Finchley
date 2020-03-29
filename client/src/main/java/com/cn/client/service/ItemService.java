package com.cn.client.service;

import com.cn.client.bean.Item;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class ItemService {

    private static final Map<Long, Item> map = new HashMap<>();

    static{
        map.put(1l,new Item(1l,"商品1","pic1","desc1",new BigDecimal(100.12)));
        map.put(2l,new Item(2l,"商品2","pic2","desc2",new BigDecimal(101.12)));
        map.put(3l,new Item(3l,"商品3","pic3","desc3",new BigDecimal(102.12)));
        map.put(4l,new Item(4l,"商品4","pic4","desc4",new BigDecimal(103.12)));
        map.put(5l,new Item(5l,"商品5","pic5","desc5",new BigDecimal(104.12)));
    }

    //模拟查询
    public Item queryById(Long id){
        return map.get(id);
    }
}
