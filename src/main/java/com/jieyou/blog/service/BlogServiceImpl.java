package com.jieyou.blog.service;

import com.jieyou.blog.NotFoundException;
import com.jieyou.blog.dao.BlogRepository;
import com.jieyou.blog.po.Blog;
import com.jieyou.blog.po.Type;
import com.jieyou.blog.util.MarkdownUtils;
import com.jieyou.blog.util.MyBeanUtils;
import com.jieyou.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author jieyou
 * @date 2020/7/29 - 15:01
 */
@Service
public class BlogServiceImpl implements BlogService
{
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id)
    {
        return blogRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id)
    {
        Blog blog = blogRepository.findById(id).get();
        if(blog == null)
        {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog)
    {
        return blogRepository.findAll(new Specification<Blog>()
        {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
            {
                List<Predicate> predicates = new ArrayList<>();
                //判断标题
                if(!"".equals(blog.getTitle()) && blog.getTitle() != null)
                {
                    predicates.add(cb.like(root.<String>get("title"),"%" + blog.getTitle() + "%"));
                }
                //判断分类
                if(blog.getTypeId() != null)
                {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                //判断推荐
                if(blog.isRecommend())
                {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable)
    {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable)
    {
        return blogRepository.findAll(new Specification<Blog>()
        {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb)
            {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable)
    {
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size)
    {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "updateTime"));
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog()
    {
        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new LinkedHashMap<>();
        for (String year : years)
        {
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog()
    {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog savaBlog(Blog blog)
    {
        if (blog.getId() == null)
        {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }
        else
        {
           blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog)
    {
        Blog b = blogRepository.findById(id).get();
        if(b == null)
        {
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id)
    {
        blogRepository.deleteById(id);
    }
}
