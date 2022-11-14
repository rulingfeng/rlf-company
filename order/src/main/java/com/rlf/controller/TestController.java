package com.rlf.controller;

import com.rlf.config.Constants;
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
public class TestController {
    @Resource
    private Constants constants;

    @GetMapping("/aaa")
    public void aaa(){
        System.out.println(constants.getType());
    }
}
