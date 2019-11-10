package com.fyj.qa.client;

import com.fyj.entity.Result;
import com.fyj.qa.service.impl.BaseClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "tensquare-base",fallback = BaseClientImpl.class)
//如果远程调用出了问题，就会自动去调用这个接口的实现类
public interface BaseClient {

    //跨模块调用接口
    @RequestMapping(value = "/label/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId);

}
