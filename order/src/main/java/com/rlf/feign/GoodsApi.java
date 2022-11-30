package com.rlf.feign;



import com.rlf.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 小富 ，公众号：程序员内点事
 */
@FeignClient(value = "goods",fallbackFactory = GoodsApiFallbackFactory.class)
public interface GoodsApi {

    /**
     * 增加商品
     * @param orderNo
     * @return
     */
    @GetMapping(value = "/goods/bbb")
    Result<String> bbbb(@RequestParam("orderNo") Long orderNo);
}
