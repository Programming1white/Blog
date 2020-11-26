package com.jieyou.blog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jieyou
 * @date 2020/7/31 - 23:08
 */
@Controller
public class AboutShowController
{
    @GetMapping("/about")
    public String about()
    {
        return "about";
    }
}
