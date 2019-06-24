package com.stylefeng.gunSelf.modular.blog.controller;

import com.google.code.kaptcha.Constants;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.cache.CacheKit;
import com.stylefeng.gunSelf.core.common.exception.InvalidKaptchaException;
import com.stylefeng.gunSelf.core.log.LogManager;
import com.stylefeng.gunSelf.core.log.factory.LogTaskFactory;
import com.stylefeng.gunSelf.core.shiro.ShiroKit;
import com.stylefeng.gunSelf.core.shiro.ShiroUser;
import com.stylefeng.gunSelf.core.util.KaptchaUtil;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.blog.service.IBlogContentService;
import com.stylefeng.gunSelf.modular.system.service.IUserService;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.stylefeng.gunSelf.core.support.HttpKit.getIp;
@Controller
@RequestMapping("/blog")
public class BlogLoginController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IBlogContentService blogContentService;

    private String PREFIX = "/blog/html/";

    @RequestMapping(value = "/blogLogin", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "";
    }

    /**
     * 跳转到主页
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {
          if (!ShiroKit.isUser()){
            model.addAttribute("tips", "请先进行登录");
            return "/Bloglogin.html";
        }
//        System.out.println(getSession().getAttributeNames());
//        Enumeration<?> enumeration = getSession().getAttributeNames();
//// 遍历enumeration中的
//        while (enumeration.hasMoreElements()) {
//// 获取session键值
//            String name = enumeration.nextElement().toString();
//            // 根据键值取session中的值
//            Object value = getSession().getAttribute(name);
//            // 打印结果
//            System.out.println("<B>" + name + "</B>=" + value + "<br>/n");
//        }

        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return "/Bloglogin.html";
        }
        if (ToolUtil.isNotEmpty(getSession().getAttribute("redirectUrl"))){
            //如果是从评论页面跳出   跳回评论页面
            String redirectUrl = getSession().getAttribute("redirectUrl") + "#ct";
            getSession().setAttribute("redirectUrl",null);
            return REDIRECT +redirectUrl;
        }

        if (ToolUtil.isNotEmpty(CacheKit.get("redirectUrl","redirectUrl"))){
            String url = CacheKit.get("redirectUrl", "redirectUrl");
            System.out.println(url);
            return REDIRECT +url;
        }
        return REDIRECT + "/blogContent/show/"+ShiroKit.getUser().getAccount();

    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout/{account}", method = RequestMethod.GET)
    public String logOut(@PathVariable String account ) {
        String url = (String) getSession().getAttribute("redirectUrl");
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        if (ToolUtil.isNotEmpty(url)){
           // redirectAttributes.addFlashAttribute("redirectUrl", url);
            CacheKit.put("redirectUrl","redirectUrl",url);
        }


        return REDIRECT + "/blogContent/show/"+account;
    }
}
