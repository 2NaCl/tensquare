package com.fyj.spit.base.controller;

import com.fyj.entity.Result;
import com.fyj.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LabelExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR,  e.getMessage());
    }
}
