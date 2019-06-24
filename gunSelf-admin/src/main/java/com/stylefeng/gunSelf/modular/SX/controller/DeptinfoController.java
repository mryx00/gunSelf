package com.stylefeng.gunSelf.modular.SX.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.Deptinfo;
import com.stylefeng.gunSelf.modular.SX.service.IDeptinfoService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

/**
 * 部门控制器
 *
 * @author fengshuonan
 * @Date 2018-07-10 10:57:52
 */
@Controller
@RequestMapping("/deptinfo")
public class DeptinfoController extends BaseController {

    private String PREFIX = "/SX/deptinfo/";

    @Autowired
    private IDeptinfoService deptinfoService;

    /**
     * 跳转到部门首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "deptinfo.html";
    }

    /**
     * 跳转到添加部门
     */
    @RequestMapping("/deptinfo_add")
    public String deptinfoAdd() {
        return PREFIX + "deptinfo_add.html";
    }

    /**
     * 跳转到修改部门
     */
    @RequestMapping("/deptinfo_update/{deptinfoId}")
    public String deptinfoUpdate(@PathVariable Integer deptinfoId, Model model) {
        Deptinfo deptinfo = deptinfoService.selectById(deptinfoId);
        model.addAttribute("item",deptinfo);
        LogObjectHolder.me().set(deptinfo);
        return PREFIX + "deptinfo_edit.html";
    }

    /**
     * 获取部门列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Deptinfo> page = new PageFactory<Deptinfo>().defaultPage();
        deptinfoService.selectPage(page,new EntityWrapper<Deptinfo>().eq("isDelete","N"));
        return super.packForBT(page);
    }

    /**
     * 新增部门
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Deptinfo deptinfo) {
        deptinfo.setIsDelete("N");
        deptinfoService.insert(deptinfo);
        return SUCCESS_TIP;
    }

    /**
     * 删除部门
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer deptinfoId) {
        deptinfoService.deleteById(deptinfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改部门
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Deptinfo deptinfo) {
        deptinfoService.updateById(deptinfo);
        return SUCCESS_TIP;
    }

    /**
     * 部门详情
     */
    @RequestMapping(value = "/detail/{deptinfoId}")
    @ResponseBody
    public Object detail(@PathVariable("deptinfoId") Integer deptinfoId) {
        return deptinfoService.selectById(deptinfoId);
    }
}
