package com.portability.users_service.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Pointcut para todos los métodos en el paquete controller
    @Pointcut("execution(* com.portability.users_service.controller..*(..))")
    public void controllerPointcut() {}

    // Pointcut para todos los métodos en el paquete service
    @Pointcut("execution(* com.portability.users_service.service..*(..))")
    public void servicePointcut() {}

    // Pointcut para todos los métodos en el paquete config
    @Pointcut("execution(* com.portability.users_service.config..*(..))")
    public void configPointcut() {}

    // Log antes de ejecutar un método
    @Before("controllerPointcut() || servicePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("→ Entering method: {}.{}() with arguments: {}", 
            className, methodName, Arrays.toString(args));
    }

    // Log después de retornar (exitosamente)
    @AfterReturning(pointcut = "controllerPointcut() || servicePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("← Exiting method: {}.{}() with result: {}", 
            className, methodName, result);
    }

    // Log después de lanzar una excepción
    @AfterThrowing(pointcut = "controllerPointcut() || servicePointcut()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.error("✗ Exception in method: {}.{}() with message: {}", 
            className, methodName, exception.getMessage(), exception);
    }

    // Log para métodos de configuración con medición de tiempo
    @Around("configPointcut()")
    public Object logAroundConfig(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.info("⚙ Configuration method started: {}.{}()", className, methodName);
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            
            logger.info("✓ Configuration method completed: {}.{}() - Time taken: {} ms", 
                className, methodName, (endTime - startTime));
            
            return result;
        } catch (Throwable throwable) {
            long endTime = System.currentTimeMillis();
            logger.error("✗ Configuration method failed: {}.{}() - Time taken: {} ms - Error: {}", 
                className, methodName, (endTime - startTime), throwable.getMessage());
            throw throwable;
        }
    }

    // Log para seguimiento de performance en servicios
    @Around("servicePointcut()")
    public Object logAroundService(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            
            if (executionTime > 1000) {
                logger.warn("⚠ Slow service method: {}.{}() - Time taken: {} ms", 
                    className, methodName, executionTime);
            } else {
                logger.debug("⏱ Service method execution time: {}.{}() - {} ms", 
                    className, methodName, executionTime);
            }
            
            return result;
        } catch (Throwable throwable) {
            long endTime = System.currentTimeMillis();
            logger.error("✗ Service method error: {}.{}() - Time taken: {} ms", 
                className, methodName, (endTime - startTime));
            throw throwable;
        }
    }
}
