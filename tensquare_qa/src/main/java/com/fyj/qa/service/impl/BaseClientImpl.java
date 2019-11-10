package com.fyj.qa.service.impl;

import com.fyj.entity.Result;
import com.fyj.entity.StatusCode;
import com.fyj.qa.client.BaseClient;
import org.springframework.stereotype.Component;

@Component
public class BaseClientImpl implements BaseClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR, "Hystrix触发了");
    }
}
