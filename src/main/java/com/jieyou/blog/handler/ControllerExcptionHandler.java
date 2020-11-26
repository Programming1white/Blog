package com.jieyou.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jieyou
 * @date 2020/7/26 - 14:23
 */
//拦截所有带Controller注解的方法
@ControllerAdvice
public class ControllerExcptionHandler
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //@ExceptionHandler标识这个方法可以做异常处理
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandle(HttpServletRequest request, Exception e) throws Exception
    {
        //记录异常信息、URL
        logger.error("Request URL : {} , Exception : {}", request.getRequestURL(),e);

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
        {
            throw e;
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
        mv.setViewName("error/error");
        return mv;
    }
}
