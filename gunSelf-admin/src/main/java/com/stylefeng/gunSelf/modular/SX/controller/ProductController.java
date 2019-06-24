package com.stylefeng.gunSelf.modular.SX.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.support.BeanKit;
import com.stylefeng.gunSelf.modular.SX.service.IProductCategoryService;
import com.stylefeng.gunSelf.modular.SX.wrapper.ProductWrapper;
import com.stylefeng.gunSelf.modular.system.model.ProductCategory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.Product;
import com.stylefeng.gunSelf.modular.SX.service.IProductService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.util.List;
import java.util.Map;

/**
 * 产品控制器
 *
 * @author fengshuonan
 * @Date 2018-07-10 16:03:09
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

    private String PREFIX = "/SX/product/";

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductCategoryService productCategoryService;

    /**
     * 跳转到产品首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "product.html";
    }

    /**
     * 跳转到添加产品
     */
    @RequestMapping("/product_add")
    public String productAdd(Model model) {
        List<ProductCategory> productCategories = productCategoryService.selectList(new EntityWrapper<ProductCategory>().eq("isDelete","N"));
        List<Map<String, Object>> maps = BeanKit.listToMapList(productCategories);
        model.addAttribute("productCategories",maps);
        return PREFIX + "product_add.html";
    }

    /**
     * 跳转到修改产品
     */
    @RequestMapping("/product_update/{productId}")
    public String productUpdate(@PathVariable Integer productId, Model model) {
        Product product = productService.selectById(productId);
        List<ProductCategory> productCategories = productCategoryService.selectList(new EntityWrapper<ProductCategory>().eq("isDelete","N"));
        model.addAttribute("item",product);
        List<Map<String, Object>> maps = BeanKit.listToMapList(productCategories);
        model.addAttribute("productCategories",maps);
        LogObjectHolder.me().set(product);
        return PREFIX + "product_edit.html";
    }

    /**
     * 获取产品列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<Product> page = new PageFactory<Product>().defaultPage();
        Page<Product> productPage = productService.selectPage(page);
        page.setRecords((List<Product>) new ProductWrapper(BeanKit.listToMapList(productPage.getRecords())).warp());
        return super.packForBT(page);
    }

    /**
     * 新增产品
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Product product) {
        productService.insert(product);
        return SUCCESS_TIP;
    }

    /**
     * 删除产品
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer productId) {
        productService.deleteById(productId);
        return SUCCESS_TIP;
    }

    /**
     * 修改产品
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Product product) {
        productService.updateById(product);
        return SUCCESS_TIP;
    }

    /**
     * 产品详情
     */
    @RequestMapping(value = "/detail/{productId}")
    @ResponseBody
    public Object detail(@PathVariable("productId") Integer productId) {
        return productService.selectById(productId);
    }
}
