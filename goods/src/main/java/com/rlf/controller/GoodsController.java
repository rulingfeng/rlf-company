package com.rlf.controller;

import com.rlf.feign.StoreApi;
import com.rlf.model.GoodsMain;
import com.rlf.service.GoodsMainService;
import com.rlf.util.Result;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsMainService goodsMainService;
    @Resource
    private StoreApi storeApi;

    @GetMapping("/bbb")
    public Result<String> bbb(Long orderNo){
        GoodsMain orderMain = new GoodsMain();

        orderMain.setOrderNo(orderNo);
        System.out.println("保存goods");
        goodsMainService.save(orderMain);
        return Result.success("goods okok");
    }

    @GetMapping("/aaa")
//    @Transactional(rollbackFor = Exception.class)
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public Result<String> aaa(Integer type){
        GoodsMain orderMain = new GoodsMain();

        orderMain.setOrderNo(777l);
        goodsMainService.save(orderMain);
        Result<String> bbbb = storeApi.test(666l);
        System.out.println(bbbb);
        int a =1/type;
        return Result.success("goods okok");
    }


}
