package com.rlf.controller;

import com.rlf.config.Constants;
import com.rlf.feign.GoodsApi;
import com.rlf.feign.StoreApi;
import com.rlf.model.OrderMain;
import com.rlf.service.OrderMainService;
import com.rlf.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @GetMapping("/aaa")
    public void aaa(){
        System.out.println(constants.getType());
    }

    @GetMapping("/bbb")
    public Result<String> bbb(){
        OrderMain orderMain = new OrderMain();
        orderMain.setOrderId(888l);
        orderMain.setOrderNo(888l);
        orderMainService.save(orderMain);
        Result<String> bbbb = goodsApi.bbbb(777l);
        System.out.println(bbbb);
        return storeApi.bbbb(666l);


    }
}
