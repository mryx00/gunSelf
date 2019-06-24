package com.stylefeng.gunSelf.modular.SX.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.support.BeanKit;
import com.stylefeng.gunSelf.modular.SX.service.IProductService;
import com.stylefeng.gunSelf.modular.SX.wrapper.InStockWrapper;
import com.stylefeng.gunSelf.modular.system.model.Product;
import com.stylefeng.gunSelf.modular.system.model.User;
import com.stylefeng.gunSelf.modular.system.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.Instock;
import com.stylefeng.gunSelf.modular.SX.service.IInstockService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.util.List;
import java.util.Map;

/**
 * 入库控制器
 *
 * @author fengshuonan
 * @Date 2018-07-11 10:59:21
 */
@Controller
@RequestMapping("/instock")
public class InstockController extends BaseController {

    private String PREFIX = "/SX/instock/";

    @Autowired
    private IInstockService instockService;

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    /**
     * 跳转到入库首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "instock.html";
    }

    /**
     * 跳转到添加入库
     */
    @RequestMapping("/instock_add")
    public String instockAdd(Model model) {
        List<Map<String, Object>> maps = BeanKit.listToMapList(iUserService.selectList(new EntityWrapper<User>().eq("status","1")));
        List<Map<String, Object>> maps1 = BeanKit.listToMapList(iProductService.selectList(null));
        model.addAttribute("users",maps);
        model.addAttribute("products",maps1);
        return PREFIX + "instock_add.html";

    }

    /**
     * 跳转到修改入库
     */
    @RequestMapping("/instock_update/{instockId}")
    public String instockUpdate(@PathVariable Integer instockId, Model model) {
        Instock instock = instockService.selectById(instockId);
        model.addAttribute("item",instock);
        List<Map<String, Object>> maps = BeanKit.listToMapList(iUserService.selectList(null));
        List<Map<String, Object>> maps1 = BeanKit.listToMapList(iProductService.selectList(null));
        model.addAttribute("users",maps);
        model.addAttribute("products",maps1);
        LogObjectHolder.me().set(instock);
        return PREFIX + "instock_edit.html";
    }

    /**
     * 获取入库列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Instock> page = new PageFactory<Instock>().defaultPage();
        instockService.selectPage(page);
        page.setRecords((List<Instock>) new InStockWrapper(BeanKit.listToMapList(page.getRecords())).warp());
        return super.packForBT(page);
    }

    /**
     * 新增入库
     */
    @RequestMapping(value = "/add")
    @Transactional(readOnly = false)
    @ResponseBody
    public Object add(Instock instock) {
        instockService.insert(instock);
        Integer instockNum = instock.getInstockNum();
        Product product = iProductService.selectById(instock.getProductId());
        product.setStockNumber(product.getStockNumber()+instockNum);
        iProductService.updateById(product);
        return SUCCESS_TIP;
    }

    /**
     * 删除入库
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer instockId) {
        instockService.deleteById(instockId);
        return SUCCESS_TIP;
    }

    /**
     * 修改入库
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Instock instock) {
        instockService.updateById(instock);
        return SUCCESS_TIP;
    }

    /**
     * 入库详情
     */
    @RequestMapping(value = "/detail/{instockId}")
    @ResponseBody
    public Object detail(@PathVariable("instockId") Integer instockId) {
        return instockService.selectById(instockId);
    }
}
