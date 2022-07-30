package com.zjm.service;

import com.zjm.domain.User;
import com.zjm.result.ResponseResult;

/**
 * @author 张佳敏
 * @Description
 * @create 2022/7/18 8:15
 **/

public interface LoginService {
    /**
     * 登录
     *
     * @param user
     * @return
     */
    ResponseResult login(User user);

    /**
     * 退出登录
     *
     * @return
     */
    ResponseResult logout();
}
