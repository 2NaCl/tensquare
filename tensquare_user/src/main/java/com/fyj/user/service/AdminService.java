package com.fyj.user.service;

import com.fyj.user.dao.AdminDao;
import com.fyj.user.pojo.Admin;
import com.fyj.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private BCryptPasswordEncoder encoder;


	/**
	 * 这里我们需要知道：
	 *
	 * 这个BCrypt加密，每次加密的结果都是不同的，和Md5不同，所以我们不能先getPwd然后加密
	 *
	 * 应该：
	 *
	 * 先用用户名查询到密码，然后用数据库的密码和用户的密码进行匹配。
	 * BCryptPasswordEncoder里这个match的用法是：
	 * 第一个参数是原密码，第二个参数是加完密的密码
	 *
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin) {
		//先根据用户名查询对象。
		Admin adminLogin = adminDao.findByLoginname(admin.getLoginname());
		//然后拿数据库中的密码和用户输入的密码匹配是否相同。
		if(adminLogin!=null && encoder.matches(admin.getPassword(), adminLogin.getPassword())){
			//保证数据库中的对象中的密码和用户输入的密码是一致的。登录成功
			return adminLogin;
		}
		//登录失败
		return null;
	}
	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Admin> findAll() {
		return adminDao.findAll();
	}


	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Admin> findSearch(Map whereMap, int page, int size) {
		Specification<Admin> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return adminDao.findAll(specification, pageRequest);
	}


	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Admin> findSearch(Map whereMap) {
		Specification<Admin> specification = createSpecification(whereMap);
		return adminDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Admin findById(String id) {
		return adminDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param admin
	 */
	public void add(Admin admin) {
		admin.setId( idWorker.nextId()+"" );
		//密码加密
		admin.setPassword(encoder.encode(admin.getPassword()));//加密方法
		adminDao.save(admin);
	}

	/**
	 * 修改
	 * @param admin
	 */
	public void update(Admin admin) {
		adminDao.save(admin);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		adminDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Admin> createSpecification(Map searchMap) {

		return new Specification<Admin>() {

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 登陆名称
                if (searchMap.get("loginname")!=null && !"".equals(searchMap.get("loginname"))) {
                	predicateList.add(cb.like(root.get("loginname").as(String.class), "%"+(String)searchMap.get("loginname")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};

	}


}
