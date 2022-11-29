package com.rlf.es.snow;

import java.util.UUID;

/**
 * @author yingyongzhi
 * @version 1.0
 * @ClassName UUIDKeyGenerator
 * @description TODO
 * @date 2020/12/10 下午1:41
 */
public class UUIDKeyGenerator {

    public UUIDKeyGenerator() {
    }

    public String getType() {
        return "UUID";
    }

    public static synchronized String generateKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
