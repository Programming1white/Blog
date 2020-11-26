package com.jieyou.blog.service;

import com.jieyou.blog.po.User;

/**
 * @author jieyou
 * @date 2020/7/28 - 15:35
 */
public interface UserService
{
    User checkUser(String username , String password);
}
