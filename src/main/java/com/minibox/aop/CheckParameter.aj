package com.minibox.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public aspect CheckParameter {

    pointcut parameterCheck():call();

    before():parameeterCheck(){
        Object[] arguments = thisJoinPoint.getArgs();
        Method method = getMethod(thisJoinPoint);

    }
}
