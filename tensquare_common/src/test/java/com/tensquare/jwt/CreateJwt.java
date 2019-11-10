package com.tensquare.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class CreateJwt {

    public static void main(String[] args) {
        //生成jwt
        JwtBuilder jwtBuilder = Jwts.builder()
                .setId("登录用户id")
                .setSubject("Username")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "salt")
                .setExpiration(new Date(new Date().getTime() + 60000))//设置过期时间,单位ms
                .claim("role", "role.Collections");//以kv存储，所以可以存放role/permission
        //将编码转化成string
        System.out.println(jwtBuilder.compact());

    }

}
