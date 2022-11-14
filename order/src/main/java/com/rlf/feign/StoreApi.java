package com.rlf.feign;



import com.rlf.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 小富 ，公众号：程序员内点事
 */
@FeignClient(value = "store")
public interface StoreApi {

    /**
     * 增加门店
     * @param orderNo
     * @return
     */
    @GetMapping(value = "/store/bbb")
    Result<String> bbbb(@RequestParam("orderNo") Long orderNo);
}
