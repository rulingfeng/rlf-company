package com.rlf.controller;

import com.google.common.collect.Lists;
import com.rlf.config.Constants;
import com.rlf.feign.GoodsApi;
import com.rlf.feign.StoreApi;
import com.rlf.model.OrderMain;
import com.rlf.service.OrderMainService;
import com.rlf.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.ArrayList;

/**
 * @author rulingfeng
 * @time 2022/11/14 08:46
 * @desc
 */
@RestController
@RequestMapping("/test")
public class OrderController {
    @Resource
    private Constants constants;
    @Autowired
    private OrderMainService orderMainService;
    @Resource
    private GoodsApi goodsApi;
    @Resource
    private StoreApi storeApi;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/aaa")
    public void aaa(){
        System.out.println(constants.getType());
    }

    @GetMapping("/bbb")
//    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.BASE)
    @Transactional
    public Result<String> bbb(Integer type){
        redisTemplate.opsForValue().set("20221128order",System.currentTimeMillis());
        ArrayList<OrderMain> objects = Lists.newArrayList();
        for (long i = 0; i < 4 ; i++) {
            OrderMain orderMain = new OrderMain();
            orderMain.setOrderId(888l);
            orderMain.setOrderNo(i);
            objects.add(orderMain);
        }
        orderMainService.saveBatch(objects);
        System.out.println("保存goods");
        Result<String> bbbb = goodsApi.bbbb(777l);
        System.out.println(bbbb);
        System.out.println("保存store");
        return storeApi.bbbb(666l,type);


    }
}
