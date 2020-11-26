package com.jieyou.blog.service;

import com.jieyou.blog.dao.UserRepository;
import com.jieyou.blog.po.User;
import com.jieyou.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jieyou
 * @date 2020/7/28 - 15:36
 */
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password)
    {
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
