package com.fyj.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 更新粉丝数和关注数
     */
    @PutMapping("/{userid}/{friendid}/{x}")
    public void updatefanscountandfollowcount(@PathVariable("userid") String userid, @PathVariable("friendid") String friendid, @PathVariable("x") int x);

}
