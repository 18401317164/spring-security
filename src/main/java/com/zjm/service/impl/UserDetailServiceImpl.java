package com.zjm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zjm.domain.LoginUser;
import com.zjm.domain.User;
import com.zjm.mapper.MenuMapper;
import com.zjm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 张佳敏
 * @Description
 * @create 2022/7/17 9:14
 **/
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //1,查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, userName);
        User user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户就抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或者密码错误");
        }
        //查询对应的权限信息
        //把权限封装到LoginUser中
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        //List<String> list = new ArrayList<>(Arrays.asList("test", "admin"));
        //把数据封装到UserDetails中返回
        return new LoginUser(user, list);
    }
}
