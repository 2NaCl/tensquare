package com.fyj.friend.controller;

import ch.qos.logback.core.status.Status;
import com.fyj.entity.Result;
import com.fyj.entity.StatusCode;
import com.fyj.friend.client.UserClient;
import com.fyj.friend.service.FriendService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest httpServletRequest;//获取token，从JwtInterceptor

    @Autowired
    private UserClient userClient;


    @PutMapping(value = "/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid,@PathVariable String type) {
        //1.验证是否登录，并且拿到当前登录用户的id
        Claims claims = (Claims)httpServletRequest.getAttribute("claims_user");
        if (claims == null) {
            return new Result(false, StatusCode.LOGINERROR, "请先登录");
        }
        //得到当前登录的用户id
        String id = claims.getId();

        //2.判断是添加好友还是添加非好友
        if (type != null) {
            if (type.equals("1")) {
                //添加好友
                int flag = friendService.addFriend(id, friendid);
                if (flag == 0) {
                    return new Result(false, StatusCode.ERROR, "不能重复添加好友");
                }
                if (flag == 1){
                    userClient.updatefanscountandfollowcount(id,friendid,1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            } else if (type.equals("2")){
                //添加非好友
                friendService.addNoFriend(id, friendid);
            }
            return new Result(false, Status.ERROR, "参数异常");
        }else {
            return new Result(false, Status.ERROR, "参数异常");
        }

    }

    @DeleteMapping("/{friendid}")
    public Result deleteFriend(@PathVariable String friendid) {
        //删除好友之前我们还是需要验证一下身份的
        Claims claims = (Claims)httpServletRequest.getAttribute("claims_user");
        if (claims == null) {
            //说明不具备当前角色
            return new Result(false, StatusCode.LOGINERROR, "权限不足");
        }
        //具备当前角色的话，我们可以首先获取到这个人的id
        String id = claims.getId();
        friendService.deleteFriend(id,friendid);
        //之前写过这个接口，其实x只能为1/-1来表示删好友还是加好友
        userClient.updatefanscountandfollowcount(id,friendid,-1);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
