package com.fyj.spit.controller;

import com.fyj.entity.PageResult;
import com.fyj.entity.Result;
import com.fyj.entity.StatusCode;
import com.fyj.spit.pojo.Spit;
import com.fyj.spit.service.SpitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(spitId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }


    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/comment/{parentId}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentId,@PathVariable int page,@PathVariable int size){
        Page<Spit> byParentId = spitService.findByParentId(parentId, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Spit>(byParentId.getTotalElements(),byParentId.getContent()));
    }

    /**
     * 点赞操作
     */
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thump(@PathVariable String spitId) {
        //判断当前用户是否已经点赞，但是现在我们没有做认证，暂时先把userid写死
        String userid = "1213";
        //判断当前用户是否已经点赞，如果检查redis有点赞的缓存，就提示无法重复点赞
        if (redisTemplate.opsForValue().get("thump_" + userid )!= null) {
            return new Result(false, StatusCode.REMOTEERROR, "无法重复点赞");
        }
        spitService.thumbp(spitId);
        //如果检测到没有点赞，就把点赞的这件事情，放入redis，
        redisTemplate.opsForValue().set("thumbup_"+userid,1);
        return new Result(true, StatusCode.OK, "点赞成功");
    }






}
