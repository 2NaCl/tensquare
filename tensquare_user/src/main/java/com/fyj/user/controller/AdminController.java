package com.fyj.user.controller;

import com.fyj.entity.Result;
import com.fyj.entity.StatusCode;
import com.fyj.user.pojo.Admin;
import com.fyj.user.service.AdminService;
import com.fyj.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 登录
	 */
	@PostMapping("/login")
	public Result login(@RequestBody Admin admin) {
		Admin login = adminService.login(admin);
		if (login == null) {
			return new Result(false, StatusCode.LOGINERROR, "登录失败");
		}
		//使得前后端可以进行通话。采用jwt进行实现。
		//生成令牌
		String token = jwtUtil.createJWT(login.getId(), login.getLoginname(), "admin");
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("role", "admin");
		return new Result(true, StatusCode.OK, "登录成功",map);
	}

	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(value = "/add",method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin  ){
		adminService.add(admin);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.update(admin);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		adminService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}
	
}
