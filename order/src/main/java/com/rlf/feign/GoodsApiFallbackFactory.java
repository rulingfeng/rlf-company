package com.rlf.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author: 茹凌丰
 * @date: 2022/4/13
 * @description:
 */
@Component
public class GoodsApiFallbackFactory implements FallbackFactory<GoodsApi> {


    @Override
    public GoodsApi create(Throwable throwable) {
        GoodsApiFallBackImpl remotePayServiceFallback = new GoodsApiFallBackImpl();
        remotePayServiceFallback.setCause(throwable);
        return remotePayServiceFallback;
    }
}
