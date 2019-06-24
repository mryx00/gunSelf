package com.stylefeng.gunSelf.modular.TS.controller;

import com.stylefeng.gunSelf.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.People;
import com.stylefeng.gunSelf.modular.TS.service.IPeopleService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

/**
 * 控制器
 *
 * @author fengshuonan
 * @Date 2018-07-25 11:51:29
 */
@Controller
@RequestMapping("/people")
public class PeopleController extends BaseController {

    private String PREFIX = "/TS/people/";

    @Autowired
    private IPeopleService peopleService;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "people.html";
    }

    /**
     * 跳转到添加
     */
    @RequestMapping("/people_add")
    public String peopleAdd() {
        return PREFIX + "people_add.html";
    }

    /**
     * 跳转到修改
     */
    @RequestMapping("/people_update/{peopleId}")
    public String peopleUpdate(@PathVariable Integer peopleId, Model model) {
        People people = peopleService.selectById(peopleId);
        model.addAttribute("item",people);
        LogObjectHolder.me().set(people);
        return PREFIX + "people_edit.html";
    }

    /**
     * 获取列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<People> page = new PageFactory<People>().defaultPage();
        peopleService.selectPage(page);
        return super.packForBT(page);
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(People people) {
        peopleService.insert(people);
        return SUCCESS_TIP;
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer peopleId) {
        peopleService.deleteById(peopleId);
        return SUCCESS_TIP;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(People people) {
        peopleService.updateById(people);
        return SUCCESS_TIP;
    }

    /**
     * 详情
     */
    @RequestMapping(value = "/detail/{peopleId}")
    @ResponseBody
    public Object detail(@PathVariable("peopleId") Integer peopleId) {
        return peopleService.selectById(peopleId);
    }
}
