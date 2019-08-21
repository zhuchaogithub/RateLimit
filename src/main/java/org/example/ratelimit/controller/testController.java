package org.example.ratelimit.controller;

import org.example.ratelimit.annotation.AccessLimit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zxc
 * @date 2019/8/21 11:04
 */
@Controller
@RequestMapping("/redis_rate_limit")
public class testController {

    @ResponseBody
    @RequestMapping("/seckill")
    @AccessLimit(limit = 2,sec = 10)  //加上自定义注解即可
    public String test (HttpServletRequest request, @RequestParam(value = "username",required = false) String userName){
        //TODO somethings……
        return   "hello world !";
    }
}
