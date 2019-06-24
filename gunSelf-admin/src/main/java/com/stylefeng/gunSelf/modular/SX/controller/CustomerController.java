package com.stylefeng.gunSelf.modular.SX.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.base.tips.MyTip;
import com.stylefeng.gunSelf.core.support.BeanKit;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.SX.wrapper.CustomerWrapper;
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
import com.stylefeng.gunSelf.modular.system.model.Customer;
import com.stylefeng.gunSelf.modular.SX.service.ICustomerService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.util.List;
import java.util.Map;

/**
 * 客户控制器
 *
 * @author fengshuonan
 * @Date 2018-07-11 10:08:18
 */
@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    private String PREFIX = "/SX/customer/";

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IUserService iUserService;

    /**
     * 跳转到客户首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "customer.html";
    }

    /**
     * 跳转到添加客户
     */
    @RequestMapping("/customer_add")
    public String customerAdd(Model model) {
        List<Map<String, Object>> maps = BeanKit.listToMapList(iUserService.selectList(new EntityWrapper<User>().eq("status","1")));
        model.addAttribute("users",maps);
        return PREFIX + "customer_add.html";
    }

    /**
     * 跳转到修改客户
     */
    @RequestMapping("/customer_update/{customerId}")
    public String customerUpdate(@PathVariable Integer customerId, Model model) {
        Customer customer = customerService.selectById(customerId);
        model.addAttribute("item",customer);
        List<Map<String, Object>> maps = BeanKit.listToMapList(iUserService.selectList(new EntityWrapper<User>().eq("status","1")));
        model.addAttribute("users",maps);
        LogObjectHolder.me().set(customer);
        return PREFIX + "customer_edit.html";
    }

    /**
     * 获取客户列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String condition,@RequestParam(required = false)String isDelete) {
        Page<Customer> page = new PageFactory<Customer>().defaultPage();
        Page<Customer> customerPage = customerService.selectPage(page);
        if (ToolUtil.isNotEmpty(condition)&&ToolUtil.isNotEmpty(isDelete)){
            List<Customer> customers = customerService.selectList(new EntityWrapper<Customer>().eq("isDelete", isDelete).and().like("customerName", condition));
            page.setRecords((List<Customer>) new CustomerWrapper(BeanKit.listToMapList(customers)).warp());
            return super.packForBT(page);
        }
        if (ToolUtil.isEmpty(condition)&&ToolUtil.isNotEmpty(isDelete)){
            List<Customer> customers = customerService.selectList(new EntityWrapper<Customer>().eq("isDelete", isDelete));
            page.setRecords((List<Customer>) new CustomerWrapper(BeanKit.listToMapList(customers)).warp());
            return super.packForBT(page);
        }
        if (ToolUtil.isNotEmpty(condition)&&ToolUtil.isEmpty(isDelete)){
            List<Customer> customers = customerService.selectList(new EntityWrapper<Customer>().like("customerName", condition));
            page.setRecords((List<Customer>) new CustomerWrapper(BeanKit.listToMapList(customers)).warp());
            return super.packForBT(page);
        }

        page.setRecords((List<Customer>) new CustomerWrapper(BeanKit.listToMapList(customerPage.getRecords())).warp());
        return super.packForBT(page);
    }



    /**
     * 新增客户
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Customer customer) {
        customerService.insert(customer);
        return SUCCESS_TIP;
    }

    /**
     * 删除客户
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer customerId) {
        Customer customer = customerService.selectById(customerId);
        customer.setIsDelete("Y");
        customerService.updateById(customer);
        return SUCCESS_TIP;
    }

    /**
     * 恢复客户
     */
    @RequestMapping(value = "/recovery")
    @ResponseBody
    public Object recovery(@RequestParam Integer customerId) {
        Customer customer = customerService.selectById(customerId);
        if(customer.getIsDelete().equals("N")){
            return new MyTip(500,"客户已经被恢复");
        }
        customer.setIsDelete("N");
        customerService.updateById(customer);
        return SUCCESS_TIP;
    }

    /**
     * 修改客户
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Customer customer) {
        customerService.updateById(customer);
        return SUCCESS_TIP;
    }

    /**
     * 客户详情
     */
    @RequestMapping(value = "/detail/{customerId}")
    @ResponseBody
    public Object detail(@PathVariable("customerId") Integer customerId) {
        return customerService.selectById(customerId);
    }


}
