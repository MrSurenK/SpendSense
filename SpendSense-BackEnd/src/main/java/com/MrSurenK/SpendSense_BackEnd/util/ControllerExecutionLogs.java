package com.MrSurenK.SpendSense_BackEnd.util;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Logging API execution, run time and meta-data when called using Spring Logging Aspect AOP
 */
@Slf4j
@Component
@Aspect
public class ControllerExecutionLogs {

    //Target all controller methods(API) when called
    @Pointcut("within(com.MrSurenK.SpendSense_BackEnd.controller..*)")
    public void controllerMethods(){};

    public Object logAround(ProceedingJoinPoint jointPoint) throws Throwable {
        String methodName = jointPoint.getSignature().toShortString();
        Object[] args = jointPoint.getArgs();

        log.info(" >>>> API called: {} | Args: {}" , methodName, Arrays.toString(args));

        long startTimer = System.currentTimeMillis();
        Object result;

        try{
            result = jointPoint.proceed();
        } catch (Exception e){
            throw e;
        }

        long duration = System.currentTimeMillis() - startTimer;
        log.info(" <<<<< API completed: {} | Duration: {}ms", methodName, duration);

        return result;
    }




}
