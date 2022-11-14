package com.rlf.controller;

import com.rlf.model.GoodsMain;
import com.rlf.service.GoodsMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/bbb")
    public void bbb(){
        GoodsMain orderMain = new GoodsMain();
        orderMain.setOrderId(777l);
        orderMain.setOrderNo(777l);
        goodsMainService.save(orderMain);

    }
}
