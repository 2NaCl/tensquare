package com.tensquare.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseJwt {

    public static void main(String[] args) {
        Claims salt = Jwts.parser().setSigningKey("salt")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiLnmbvlvZXnlKjmiLdpZCIsInN1YiI6IlVzZXJuYW1lIiwiaWF0IjoxNTcyNDE4ODQ3LCJleHAiOjE1NzI0MTg5MDd9.Xaayf3FqOmsfmTYItnCkZxv6_eCZH-ab3ORqE5yElds")
                .getBody();

        System.out.println("用户id："+salt.getId());
        System.out.println("用户登录时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(salt.getIssuedAt()));
        System.out.println("用户过期时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(salt.getExpiration()));
        System.out.println("用户名："+salt.getSubject());
        System.out.println("角色名："+salt.get("role"));
    }

}
