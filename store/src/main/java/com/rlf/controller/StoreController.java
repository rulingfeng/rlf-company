package com.rlf.controller;

import com.rlf.model.StoreMain;

import com.rlf.service.StoreMainService;
import com.rlf.util.Result;
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
@RequestMapping("/store")
public class StoreController {

    @Resource
    private StoreMainService storeMainService;


    @GetMapping("/bbb")
    public Result<String> bbb(Long orderNo){
        StoreMain storeMain = new StoreMain();
       // int a = 1/0;
        storeMain.setOrderNo(orderNo);
        storeMainService.save(storeMain);
        return Result.success("store okok");
    }
    @GetMapping("/test")
    public Result<String> test(Long orderNo){
        StoreMain storeMain = new StoreMain();
        storeMain.setOrderNo(orderNo);
        storeMainService.save(storeMain);
        return Result.success("store okok");
    }
}
