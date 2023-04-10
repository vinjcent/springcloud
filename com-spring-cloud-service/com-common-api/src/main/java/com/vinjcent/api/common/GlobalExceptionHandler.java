package com.vinjcent.api.common;

import com.vinjcent.api.exception.SystemException;
import com.vinjcent.api.status.enums.AppHttpCodeEnum;
import com.vinjcent.api.status.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author vinjcent
 * 自定义controller异常处理,全局捕获异常
 */
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕捉 SystemException 异常
     *
     * @param e 异常
     */
    @ExceptionHandler(SystemException.class)
    public ResponseResult<Object> systemExceptionHandler(SystemException e) {
        // 打印异常信息
        log.error("出现了系统异常!    :{}", e.getMessage());

        // 从异常中对象中获取提示信息封装返回
        return ResponseResult.error(e.getCode(), e.getMsg());
    }

    ///**
    // * 捕捉普通异常(用于后端测试)
    // * @param e 异常
    // */
    //@ExceptionHandler(Exception.class)
    // public ResponseResult<Object> exceptionHandler(Exception e) {
    //    // 打印异常信息
    //    log.error("出现了普通异常!    :{}", e.getMessage());
    //
    //    // 从异常中对象中获取提示信息封装返回
    //    return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(), e.getMessage());
    //}

    /**
     * 捕捉不合法字段异常
     *
     * @param e 异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<?> bindExceptionHandler(BindException e) {
        log.error("BindException数据绑定异常:    {}", e.getMessage());

        StringBuilder sb = new StringBuilder();
        // 获取实体字段 message 内容
        List<FieldError> fieldErrors = e.getFieldErrors();
        fieldErrors.forEach((error) ->
                sb.append("参数:[").append(error.getObjectName())
                        .append(".").append(error.getField())
                        .append("]的传入值:[").append(error.getRejectedValue()).append("]与预期的字段类型不匹配.")
        );
        return ResponseResult.error(AppHttpCodeEnum.SERVER_ERROR.getCode(), sb.toString());
    }

    /**
     * jsr 规范中的验证异常,嵌套检验问题
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<Object> constraintViolationException(ConstraintViolationException e) {
        log.error("ConstraintViolationException数据类型限制异常:    {}", e.getMessage());

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        String msg = violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";"));

        return ResponseResult.error(AppHttpCodeEnum.SERVER_ERROR.getCode(), msg);
    }

    /**
     * 方法参数异常
     *
     * @param e 异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Object> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException方法参数不合法异常:    {}", e.getMessage());
        StringBuffer sb = new StringBuffer();
        // 获取实体字段 message 内容
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        fieldErrors.forEach((error) ->
                sb.append(error.getDefaultMessage())
                        .append("'; ")
        );
        return ResponseResult.error(AppHttpCodeEnum.SERVER_ERROR.getCode(), sb.substring(0, sb.length() - 1));
    }

}
