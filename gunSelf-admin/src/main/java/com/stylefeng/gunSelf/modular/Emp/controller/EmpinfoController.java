package com.stylefeng.gunSelf.modular.Emp.controller;

import com.stylefeng.gunSelf.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.Empinfo;
import com.stylefeng.gunSelf.modular.Emp.service.IEmpinfoService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2018-07-09 15:42:25
 */
@Controller
@RequestMapping("/empinfo")
public class EmpinfoController extends BaseController {

    private String PREFIX = "/Emp/empinfo/";

    @Autowired
    private IEmpinfoService empinfoService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "empinfo.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/empinfo_add")
    public String empinfoAdd() {
        return PREFIX + "empinfo_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/empinfo_update/{empinfoId}")
    public String empinfoUpdate(@PathVariable Integer empinfoId, Model model) {
        Empinfo empinfo = empinfoService.selectById(empinfoId);
        model.addAttribute("item",empinfo);
        LogObjectHolder.me().set(empinfo);
        return PREFIX + "empinfo_edit.html";
    }

    /**
     * 获取列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Empinfo> page = new PageFactory<Empinfo>().defaultPage();
        empinfoService.selectPage(page);
        return super.packForBT(page);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Empinfo empinfo) {
        empinfoService.insert(empinfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer empinfoId) {
        empinfoService.deleteById(empinfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Empinfo empinfo) {
        empinfoService.updateById(empinfo);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{empinfoId}")
    @ResponseBody
    public Object detail(@PathVariable("empinfoId") Integer empinfoId) {
        return empinfoService.selectById(empinfoId);
    }
}
