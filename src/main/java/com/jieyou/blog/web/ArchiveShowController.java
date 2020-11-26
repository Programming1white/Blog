package com.jieyou.blog.web;

import com.jieyou.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jieyou
 * @date 2020/7/31 - 22:22
 */
@Controller
public class ArchiveShowController
{
    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model)
    {
        model.addAttribute("archiveMap",blogService.archiveBlog());
        model.addAttribute("blogCount",blogService.countBlog());
        return "archives";
    }
}
