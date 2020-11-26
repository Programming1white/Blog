package com.jieyou.blog.dao;

import com.jieyou.blog.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jieyou
 * @date 2020/7/28 - 15:37
 */
public interface UserRepository extends JpaRepository<User,Long>
{
    User findByUsernameAndPassword(String name , String password);
}
