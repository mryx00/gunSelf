package com.stylefeng.gunSelf.modular.SX.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.support.BeanKit;
import com.stylefeng.gunSelf.modular.SX.service.ICustomerService;
import com.stylefeng.gunSelf.modular.SX.service.IProductService;
import com.stylefeng.gunSelf.modular.system.model.Customer;
import com.stylefeng.gunSelf.modular.system.model.User;
import com.stylefeng.gunSelf.modular.system.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.Orderitem;
import com.stylefeng.gunSelf.modular.SX.service.IOrderitemService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 *
 * @author fengshuonan
 * @Date 2018-07-11 15:25:11
 */
@Controller
@RequestMapping("/orderitem")
public class OrderitemController extends BaseController {

    private String PREFIX = "/SX/orderitem/";

    @Autowired
    private IOrderitemService orderitemService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICustomerService iCustomerService;

    @Autowired
    private IProductService iProductService;

    /**
     * 跳转到订单首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "orderitem.html";
    }

    /**
     * 跳转到添加订单
     */
    @RequestMapping("/orderitem_add")
    public String orderitemAdd(Model model) {
        List<Map<String, Object>> maps = BeanKit.listToMapList(iUserService.selectList(new EntityWrapper<User>().eq("status","1")));
        List<Map<String, Object>> maps1 = BeanKit.listToMapList(iCustomerService.selectList(new EntityWrapper<Customer>().eq("isDelete","N")));
        model.addAttribute("users",maps);
        model.addAttribute("customers",maps1);
        return PREFIX + "orderitem_add.html";
    }

    /**
     * 跳转到修改订单
     */
    @RequestMapping("/orderitem_update/{orderitemId}")
    public String orderitemUpdate(@PathVariable Integer orderitemId, Model model) {
        Orderitem orderitem = orderitemService.selectById(orderitemId);
        model.addAttribute("item",orderitem);
        LogObjectHolder.me().set(orderitem);
        return PREFIX + "orderitem_edit.html";
    }

    /**
     * 获取订单列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Orderitem> page = new PageFactory<Orderitem>().defaultPage();
        orderitemService.selectPage(page);
        return super.packForBT(page);
    }

    /**
     * 新增订单
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Orderitem orderitem) {
        orderitemService.insert(orderitem);
        return SUCCESS_TIP;
    }

    /**
     * 删除订单
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer orderitemId) {
        orderitemService.deleteById(orderitemId);
        return SUCCESS_TIP;
    }

    /**
     * 修改订单
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Orderitem orderitem) {
        orderitemService.updateById(orderitem);
        return SUCCESS_TIP;
    }

    /**
     * 订单详情
     */
    @RequestMapping(value = "/detail/{orderitemId}")
    @ResponseBody
    public Object detail(@PathVariable("orderitemId") Integer orderitemId) {
        return orderitemService.selectById(orderitemId);
    }
}
