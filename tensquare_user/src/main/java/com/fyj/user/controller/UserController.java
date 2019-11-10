package com.fyj.user.controller;
import com.fyj.entity.Result;
import com.fyj.entity.StatusCode;
import com.fyj.user.pojo.User;
import com.fyj.user.service.UserService;
import com.fyj.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisTemplate redisTemplate;

    @Autowired
    private JwtUtil jwtUtil;

	/**
	 * 更新粉丝数和关注数
	 */
	@PutMapping("/{userid}/{friendid}/{x}")
	public void updatefanscountandfollowcount(@PathVariable String userid,@PathVariable String friendid , @PathVariable int x) {
		userService.updatefanscountandfollowcount(userid, friendid,x);
	}


	/**
	 * 登录
	 */
	@PostMapping("/login")
	public Result login(@RequestBody User user) {
        user = userService.login(user.getMobile(),user.getPassword());
        if (user == null) {
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
        }
		String token = jwtUtil.createJWT(user.getId(), user.getMobile(), "user");
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("role", user);
        return new Result(true, StatusCode.OK, "登录成功",map);
    }


	/**
	 * 发送短信验证码
	 */
	@PostMapping(value = "/sendsms/{mobile}")
	public Result sendSms(@PathVariable String mobile) {
		userService.sendSms(mobile);
		return new Result(true, StatusCode.OK, "发送成功");
	}

	/**
	 * 用户注册
	 */
	@PostMapping("/register/{code}")
	public Result regist(@PathVariable String code,@RequestBody User user){
		//从redis中取出验证码，如果没有，默认为null
		String checkCodeRedis = redisTemplate.opsForValue().get("checkCode_" + user.getMobile()).toString();//从redis取一下
		if (checkCodeRedis.isEmpty()) {
			return new Result(false,StatusCode.ERROR,"请先获取短信验证码");
		}
		if (!checkCodeRedis.equals(code)) {
			return new Result(false, StatusCode.ERROR, "请输入正确的验证码");
		}
		userService.add(user);
		return new Result(true, StatusCode.OK, "注册成功");
	}



	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}



	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }

	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除 必须有admin角色才能删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);

		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
