package com.rlf.es.snow;

/**
 * KeyUtils.getSnakeflakeStringKey();
 * @author yingyongzhi
 * @version 1.0
 * @ClassName KeyUtils
 * @description TODO
 * @date 2020/12/10 上午11:08
 */
public class KeyUtils {

    private KeyUtils() {
    }

    public static long getSnakeflakeKey() {
        return (ServiceUtils.getService(SnowflakeKeyGenerator.class)).generateKey();
    }

    public static String getSnakeflakeStringKey() {
        return String.valueOf(getSnakeflakeKey());
    }

    public static String getUUIDKey() {
        return UUIDKeyGenerator.generateKey();
    }

    public static long[] getSnakeflakeKeys(int number) {
        if (number < 1) {
            return new long[0];
        } else {
            long[] retArray = new long[number];

            for(int i = 0; i < number; ++i) {
                retArray[i] = getSnakeflakeKey();
            }

            return retArray;
        }
    }

    public static String[] getSnakeflakeStringKeys(int number) {
        if (number < 1) {
            return new String[0];
        } else {
            String[] retArray = new String[number];

            for(int i = 0; i < number; ++i) {
                retArray[i] = getSnakeflakeStringKey();
            }

            return retArray;
        }
    }

    public static String[] getUUIDKeys(int number) {
        if (number < 1) {
            return new String[0];
        } else {
            String[] retArray = new String[number];

            for(int i = 0; i < number; ++i) {
                retArray[i] = getUUIDKey();
            }

            return retArray;
        }
    }
}
