package com.zjm.service.impl;

import com.zjm.domain.LoginUser;
import com.zjm.domain.User;
import com.zjm.result.ResponseResult;
import com.zjm.service.LoginService;
import com.zjm.utils.JwtUtil;
import com.zjm.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author 张佳敏
 * @Description
 * @create 2022/7/18 8:16
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        //1.AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //2.如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //3.如果认证通过了，使用userid生成JWT通过ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String jwt = JwtUtil.createJWT(String.valueOf(loginUser.getUser().getId()));
        //4.把完整的用户信息存入redis userid作为key
        redisCache.setCacheObject("login:" + loginUser.getUser().getId(), loginUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult(200, "登录成功", map);
    }

    @Override
    public ResponseResult logout() {
        //1.获取SecurityContextHolder中的userid
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //2.删除redis中的用户数据
        redisCache.deleteObject("login:" + loginUser.getUser().getId());
        return new ResponseResult(200, "注销成功");
    }
}
