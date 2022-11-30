package com.rlf.feign;

import com.rlf.util.Result;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author rulingfeng
 * @time 2022/11/30 10:39
 * @desc
 */
@Component
@Slf4j
public class GoodsApiFallBackImpl implements GoodsApi{
    @Setter
    Throwable cause;
    @Override
    public Result<String> bbbb(Long orderNo) {
        System.out.println("bbbb失败------------");
        log.error("降级错误",cause);
        return Result.error("降级成功"+cause.getMessage());
    }
}
