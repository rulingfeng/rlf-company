package com.rlf.es.snow;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yingyongzhi
 * @version 1.0
 * @ClassName ServiceUtils
 * @description TODO
 * @date 2020/12/10 下午1:45
 */
@Component
public class ServiceUtils implements BeanFactoryPostProcessor {
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ServiceUtils.beanFactory = beanFactory;
    }

    public static <T> T getService(String name, Class<T> clazz) {
        return beanFactory.getBean(name, clazz);
    }

    public static <T> T getService(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public static Object getService(String name) {
        return containsBean(name) ? beanFactory.getBean(name) : null;
    }

    public ServiceUtils() {
    }

    private <T> T getBean(String name, Class<T> clazz) {
        return beanFactory.getBean(name, clazz);
    }

    private <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    private Object getBean(String name) {
        return beanFactory.getBean(name);
    }

    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    public static <T> List<T> getBeans(Class<T> clazz) {
        Map<String, T> map = new HashMap();
        String[] beanNames = beanFactory.getBeanNamesForType(clazz);
        if (beanNames != null) {
            String[] var3 = beanNames;
            int var4 = beanNames.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String name = var3[var5];
                T bean = getService(name, clazz);
                if (bean != null) {
                    map.put(name, bean);
                }
            }
        }

        return new ArrayList(map.values());
    }

    public static String getActiveProfile() {
        String active = null;
        String[] profiles = ((Environment)beanFactory.getBean(Environment.class)).getActiveProfiles();
        if (null != profiles && profiles.length > 0) {
            active = profiles[0];
        }

        return active;
    }

    public static String getPropertyByKey(String key) {
        return ((Environment)beanFactory.getBean(Environment.class)).getProperty(key);
    }
}
