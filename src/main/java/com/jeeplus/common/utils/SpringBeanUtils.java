package com.jeeplus.common.utils;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


public class SpringBeanUtils implements BeanFactoryAware {

    private static BeanFactory beanFactory;

    /**
     * 注入BeanFactory实例
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringBeanUtils.beanFactory = beanFactory;
    }

    /**
     * 根据bean的名称获取相应类型的对象
     *
     * @param beanName bean的名称
     * @return Object类型的对象
     */
    public static Object getBeanByName(String beanName) {
        return beanFactory.getBean(beanName);
    }

    /**
     * 根据bean的类型获取相应类型的对象
     *
     * @param clazz bean的类型，没有使用泛型
     * @return Object类型的对象
     */
    public static Object getBeanByClass(Class clazz) {
        WebApplicationContext wac = ContextLoader
                .getCurrentWebApplicationContext();
        Object bean = wac.getBean(clazz);
        return bean;
    }

    /**
     * 根据bean的名称获取相应类型的对象
     *
     * @param clazz bean的类型，使用泛型
     * @return T类型的对象
     */
    public static <T> T getGenericsBeanByClass(Class<T> clazz) {
        WebApplicationContext wac = ContextLoader
                .getCurrentWebApplicationContext();
        T bean = wac.getBean(clazz);
        return bean;
    }

}