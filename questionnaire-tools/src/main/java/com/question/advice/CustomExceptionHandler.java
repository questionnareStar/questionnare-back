package com.question.advice;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.question.domain.Result;
import com.question.enums.ResultEnum;
import com.question.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 全局异常处理
 * @Author 问卷星球团队
 * @Date 2021.1.20
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {


    /**
     * 处理运行时异常
     */
    @Order(99)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Throwable.class)
    public Result handle(Throwable e, HttpServletRequest request) {

        log.error("系统异常"+request.getRequestURI()+e);
        return new Result(ResultEnum.ERROR,e.getMessage());
    }

    /**
     * 自定义异常：处理参数不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleBindException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError fe : fieldErrors) {
            sb.append(fe.getDefaultMessage());
        }
        return new Result(50001,sb.toString(),sb.toString());
    }

    /**
     * 登录异常
     */
    @ExceptionHandler(NotLoginException.class)
    public Result handleNotLoginException(NotLoginException e) {
        return new Result(ResultEnum.LOGIN_ERROR,e.getMessage());
    }

    /**
     * 角色权限不足
     */
    @ExceptionHandler(NotRoleException.class)
    public Result handleNotLoginException(NotRoleException e) {
        return new Result(ResultEnum.ROLE_ERROR,e.getMessage());
    }


    /**
     * 用户注册，账号重复
     */
    @ExceptionHandler(Exception.class)
    public Result Exception(Exception e) {
        return new Result(ResultEnum.ERROR,e.getMessage());
    }


    /**
     * 运行异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Result RuntimeException(RuntimeException e) {
        log.error("运行异常"+e);
        return new Result(ResultEnum.RUNTIME_ERROR,e.getMessage());
    }


    /**
     * 用户未注册
     */
    @ExceptionHandler(NoRegisterException.class)
    public Result NoRegisterException(NoRegisterException e) {
        return new Result(ResultEnum.NO_REGISTER,e.getMessage());
    }


    /**
     * 默认异常处理
     */
    @ExceptionHandler(DefaultException.class)
    public Result DefaultException(DefaultException e) {
        return new Result(20001,e.getMessage(),null);
    }
}