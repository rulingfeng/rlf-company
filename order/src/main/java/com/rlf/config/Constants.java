package com.rlf.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author rulingfeng
 * @time 2022/11/14 08:43
 * @desc
 */
@Component
public class Constants {

    @Value("${order.type}")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
