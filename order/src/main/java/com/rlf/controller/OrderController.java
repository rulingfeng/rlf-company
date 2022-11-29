package com.rlf.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.rlf.config.Constants;
import com.rlf.es.EsOrder;
import com.rlf.es.snow.KeyUtils;
import com.rlf.feign.GoodsApi;
import com.rlf.feign.StoreApi;
import com.rlf.model.OrderMain;
import com.rlf.service.OrderMainService;
import com.rlf.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

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
    @Resource
    private RedissonClient redissonClient;
    @Autowired
    private RestHighLevelClient highLevelClient;

    @GetMapping("/aaa")
    public void aaa(){
        System.out.println(constants.getType());
    }
    @GetMapping("/redisson")
    public String redisson(){
        RLock creagggggg = redissonClient.getLock("creagggggg");
        creagggggg.lock();
        creagggggg.unlock();
        return creagggggg.getName();
    }

    @GetMapping("/bbb")
//    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.BASE)
    @Transactional
    public Result<String> bbb(Integer type)throws IOException {
        EsOrder esOrder = new EsOrder();
        esOrder.setId(8888l);
        esOrder.setOrderNo(KeyUtils.getSnakeflakeStringKey());
        esOrder.setOrderName("鲜奶味道巧克力面包吐司很香的");
        esOrder.setPrice(19.6);
        esOrder.setStartTime(new Date());
        esOrder.setEndTime(new Date());
        // 拿到索引
        IndexRequest request = new IndexRequest("user_order");
        // 设置文档id
        request.id("8888");

        // 将User对象转化为JSON，数据放入请求
        request.source(JSON.toJSONString(esOrder), XContentType.JSON);
        // 客户端发送请求后获取响应
        IndexResponse index = highLevelClient.index(request, RequestOptions.DEFAULT);

        System.out.println(index.toString());
        // 索引状态
        System.out.println(index.status());


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
