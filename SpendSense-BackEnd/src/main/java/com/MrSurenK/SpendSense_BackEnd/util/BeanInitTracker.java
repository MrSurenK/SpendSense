package com.MrSurenK.SpendSense_BackEnd.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class BeanInitTracker implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(BeanInitTracker.class);
    private int counter = 0;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        // Only log your own beans
        if (bean.getClass().getPackageName().startsWith("com.MrSurenK")) {
            log.debug(String.format("%03d BEFORE -> %s (%s)",
                    ++counter, beanName, bean.getClass().getSimpleName()));
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if(bean.getClass().getPackageName().startsWith("com.MrSurenK")) {
            try {
                log.debug(String.format("%03d AFTER  -> %s", counter, beanName));
            } catch (Exception e) {
                // If any exception happens during initialization or in a proxy, we log it
                log.error(String.format("%03d ERROR -> %s", counter, beanName), e);
            }
        }
        return bean;
    }
}