package com.learn.blog.util;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GloabalExceptionHandler {


    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public Object defaultExceptionHandler(HttpServletRequest req,Exception e) {


        return Result.fail("对不起，你没有访问权限！");
    }
    @ExceptionHandler(value=Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception
    {
        e.printStackTrace();
        return "捕获的错误 " + e.getMessage();
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("exception" , e);
//        mav.addObject("url" , req.getRequestURL());
//        mav.setViewName("errorPage");
//        return mav;
    }

}
