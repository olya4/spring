package com.geekbrains.homework3.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AppAspect {

    private long durationProductService;
    private long durationOrderService;
    private long durationUserService;

    @Around("execution(public * com.geekbrains.homework3.services.ProductService.*(..))")
    public Object methodProfilingForProductService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        durationProductService = end - begin;
        log.info("Время выполнения ProductService = " + durationProductService);
        return out;
    }

    @Around("execution(public * com.geekbrains.homework3.services.OrderService.*(..))")
    public Object methodProfilingForOrderService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        durationOrderService = end - begin;
        log.info("Время выполнения OrderService = " + durationOrderService);
        return out;
    }

    @Around("execution(public * com.geekbrains.homework3.services.UserService.*(..))")
    public Object methodProfilingForUserService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        durationUserService = end - begin;
        log.info("Время выполнения UserService = " + durationUserService);
        return out;
    }

    public long getDurationProductService() {
        return durationProductService;
    }

    public long getDurationOrderService() {
        return durationOrderService;
    }

    public long getDurationUserService() {
        return durationUserService;
    }
}
