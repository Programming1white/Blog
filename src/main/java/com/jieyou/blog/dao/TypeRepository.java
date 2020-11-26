package com.jieyou.blog.dao;

import com.jieyou.blog.po.Type;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author jieyou
 * @date 2020/7/28 - 22:07
 */
public interface TypeRepository extends JpaRepository<Type,Long>
{
    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
