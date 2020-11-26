package com.jieyou.blog.service;

import com.jieyou.blog.NotFoundException;
import com.jieyou.blog.dao.TagRepository;
import com.jieyou.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jieyou
 * @date 2020/7/29 - 9:48
 */
@Service
public class TagServiceImpl implements TagService
{
    @Autowired
    TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag)
    {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id)
    {
        return tagRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Tag getTagByname(String name)
    {
        return tagRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable)
    {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag()
    {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTagTop(Integer size)
    {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "blogs.size"));
        return tagRepository.findTop(pageable);
    }

    @Override
    public List<Tag> listTag(String ids)
    {
        return tagRepository.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids)
    {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null)
        {
            String[] idarray = ids.split(",");
            for (int i = 0;i < idarray.length; i++)
            {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag)
    {
        Tag t = tagRepository.findById(id).get();
        if(t == null)
        {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id)
    {
        tagRepository.deleteById(id);
    }
}
