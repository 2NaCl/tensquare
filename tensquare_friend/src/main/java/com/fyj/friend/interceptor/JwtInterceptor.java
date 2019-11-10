package com.fyj.friend.interceptor;

import com.fyj.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        //无论如何放行，具体能不能操作还是在具体的操作中去判断。
        //拦截器只负责把请求头中包含token的令牌进行一个解析验证
        String header = request.getHeader("Authorization");
        if (header!= null && !"".equals(header)) {
            if (!header.startsWith("Bearer ")) {
                throw new RuntimeException("权限不足");
            }
            //得到token
            String token = header.substring(7);
            //对令牌进行检验
            try {
                Claims claims = jwtUtil.parseJWT(token);
                String role = claims.get("role").toString();
                if (role != null || role.equals("admin")) {
                    request.setAttribute("claims_admin", claims);
                }
                if (role != null || role.equals("user")) {
                    request.setAttribute("claims_user", claims);
                }
            } catch (Exception e) {
                throw new RuntimeException("令牌不正确");
            }
        }
        return true;
    }
}
