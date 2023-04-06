package com.vinjcent.api.aspect;

import com.alibaba.fastjson.JSON;
import com.vinjcent.api.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author vinjcent
 * 请求切面,用于controller中的接口注解
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    /**
     * 定义切点
     */
    @Pointcut("@annotation(com.vinjcent.api.annotation.SystemLog)")
    public void pointcut() {

    }

    /**
     * 执行环绕通知
     *
     * @param joinPoint 被增强方法的信息对象
     * @return 执行完方法后的返回值
     */
    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object res;       // 目标方法的调用,执行完方法之后的返回值(结果)
        try {
            handleBefore(joinPoint);
            res = joinPoint.proceed();
            handleAfter(res);
        } finally {
            // 结束后换行
            log.info("========End========" + System.lineSeparator());
        }
        return res;
    }

    /**
     * 后置处理
     *
     * @param res 结果
     */
    private void handleAfter(Object res) {
        // 打印出参
        log.info("Response      :{}", JSON.toJSONString(res));
    }

    /**
     * 前置处理
     *
     * @param joinPoint 关联点
     */
    private void handleBefore(ProceedingJoinPoint joinPoint) {
        log.info("========Start========");

        // 获取ThreadLocal(本地线程)下的请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取当前请求
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        // 打印 URL
        log.info("URL           :{}", request.getRequestURL());

        // 打印描述信息
        log.info("BusinessName  :{}", systemLog.businessName());

        // 打印 Http Method
        log.info("Http Method   :{}", request.getMethod());

        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method  :{}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature());

        // 打印请求的 IP
        log.info("IP            :{}", request.getRemoteHost());

        // 打印请求入参(是一个数组,转为json)
        log.info("Request Args  :{}", JSON.toJSONString(joinPoint.getArgs()));
    }

    /**
     * 获取当前SystemLog注解的对象
     *
     * @param joinPoint 关联点
     * @return 返回当前注解
     */
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {

        // 由于使用该注解的方法封装成了签名,需要对签名进行强转为"方法",从而获取签名中方法的注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getAnnotation(SystemLog.class);
    }


}
