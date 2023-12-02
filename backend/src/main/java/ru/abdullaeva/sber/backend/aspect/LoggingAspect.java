package ru.abdullaeva.sber.backend.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Аспект для логирования методов сервисов и запросов контроллера
 */
@Component
@Aspect
public class LoggingAspect {
    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    /**
     * Точка среза для сервисов.
     */
    @Pointcut("within(ru.abdullaeva.sber.backend.documents.service.impl.DocumentServiceImpl)")
    public void documentServicePointCut() {

    }

    /**
     * Точка среза для контроллера.
     */
    @Pointcut("within(ru.abdullaeva.sber.backend.documents.controller.*)")
    public void controllerPointCut() {

    }

    /**
     * Advice - набор инструкций, выполняемых вокруг методов сервисов.
     * @param joinPoint - точка выполнения программы - вызов метода.
     */
    @Around("documentServicePointCut()")
    public Object documentServiceMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        logger.log(Level.INFO, joinPoint.getSignature() + " method call, parameters: " + List.of(args));
        Object result = joinPoint.proceed();
        if (result != null) {
            logger.log(Level.INFO, joinPoint.getSignature() + "  method was called with result: " + result);
        }
        else {
            logger.log(Level.INFO, joinPoint.getSignature() + "  method was called with result: null");
        }
        return result;
    }

    /**
     * Advice - набор инструкций, выполняемых вокруг запросов контроллера.
     * @param joinPoint - точка выполнения программы - вызов запроса.
     */
    @Around("controllerPointCut()")
    public Object controllerMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        logger.log(Level.INFO, joinPoint.getSignature() + " method call, parameters: " + List.of(args));
        Object result = joinPoint.proceed();
        if (result != null) {
            logger.log(Level.INFO, joinPoint.getSignature() + " method was called with result: " + result);
        }
        else {
            logger.log(Level.INFO, joinPoint.getSignature() + " method was called with result: null");
        }
        return result;
    }
}
