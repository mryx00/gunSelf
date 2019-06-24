package com.stylefeng.gunSelf.modular.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.common.constant.state.ManagerStatus;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.modular.blog.service.IBlogContentService;
import com.stylefeng.gunSelf.modular.system.factory.UserFactory;
import com.stylefeng.gunSelf.modular.system.model.User;
import com.stylefeng.gunSelf.modular.system.service.IUserService;
import com.stylefeng.gunSelf.modular.system.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/blog")
public class BlogRegisterController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IBlogContentService blogContentService;

    private String PREFIX = "/blog/html/";

    /**
     * 进行注册
     * @return
     */
    @RequestMapping(value = "/RegisterBlog", method = RequestMethod.POST)
    public Object RegisterBlog(Model model) {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        int count = userService.selectCount(new EntityWrapper<User>().eq("account", username));
        if (count>0){
            //return new ErrorTip(400,"该用户名已经被注册，请更换用户名");
            model.addAttribute("tips", "该用户名已经被注册，请更换用户名");
            return PREFIX+"BlogRegister.html";
        }else{
            UserDto user = new UserDto();
            user.setPassword(password);
            user.setAccount(username);
            user.setSalt(ShiroKit.getRandomSalt(5));
            user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
            user.setStatus(ManagerStatus.OK.getCode());
            user.setCreatetime(new Date());
            this.userService.insert(UserFactory.createUser(user));
            userService.setRoles(user.getId(),"6");
        }


        return REDIRECT + "";
    }

    /**
     * 跳转到注册页面
     */
    @RequestMapping(value = "/blogRegister", method = RequestMethod.GET)
    public String index(Model model) {
          return PREFIX+"BlogRegister.html";
    }


}
