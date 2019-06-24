package com.stylefeng.gunSelf.modular.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by liuruijie on 2017/5/12.
 */
@Controller
public class PageController {
    @GetMapping("/editor")
    public String test(){
        System.out.println("aaa");
        return "/static/modeler";
    }
}
