package com.stylefeng.gunSelf.modular.SX.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.gunSelf.core.base.controller.BaseController;
import com.stylefeng.gunSelf.core.base.tips.MyTip;
import com.stylefeng.gunSelf.core.util.ToolUtil;
import com.stylefeng.gunSelf.modular.SX.service.IProductService;
import com.stylefeng.gunSelf.modular.system.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.gunSelf.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.gunSelf.modular.system.model.ProductCategory;
import com.stylefeng.gunSelf.modular.SX.service.IProductCategoryService;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.core.common.constant.factory.PageFactory;

import java.util.List;

/**
 * 类别控制器
 *
 * @author fengshuonan
 * @Date 2018-07-10 15:40:43
 */
@Controller
@RequestMapping("/productCategory")
public class ProductCategoryController extends BaseController {

    private String PREFIX = "/SX/productCategory/";

    @Autowired
    private IProductCategoryService productCategoryService;

    @Autowired
    private IProductService productService;

    /**
     * 跳转到类别首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "productCategory.html";
    }

    /**
     * 跳转到添加类别
     */
    @RequestMapping("/productCategory_add")
    public String productCategoryAdd() {
        return PREFIX + "productCategory_add.html";
    }

    /**
     * 跳转到修改类别
     */
    @RequestMapping("/productCategory_update/{productCategoryId}")
    public String productCategoryUpdate(@PathVariable Integer productCategoryId, Model model) {
        ProductCategory productCategory = productCategoryService.selectById(productCategoryId);
        model.addAttribute("item",productCategory);
        LogObjectHolder.me().set(productCategory);
        return PREFIX + "productCategory_edit.html";
    }

    /**
     * 获取类别列表,带分页功能bizEnBigName
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false)String condition,@RequestParam(required = false)String isDelete) {
        Page<ProductCategory> page = new PageFactory<ProductCategory>().defaultPage();
        if (ToolUtil.isNotEmpty(condition)&&ToolUtil.isNotEmpty(isDelete)){
            List<ProductCategory> categorys = productCategoryService.selectList(new EntityWrapper<ProductCategory>().like("categoryName", condition).and().eq("isDelete",isDelete));
            page.setRecords(categorys);
            return super.packForBT(page);
        }
        if (ToolUtil.isEmpty(condition)&&ToolUtil.isNotEmpty(isDelete)){
            List<ProductCategory> categorys = productCategoryService.selectList(new EntityWrapper<ProductCategory>().eq("isDelete", isDelete));
            page.setRecords(categorys);
            return super.packForBT(page);
        }
        if (ToolUtil.isNotEmpty(condition)&&ToolUtil.isEmpty(isDelete)){
            List<ProductCategory> categorys = productCategoryService.selectList(new EntityWrapper<ProductCategory>().like("categoryName", condition));
            page.setRecords(categorys);
            return super.packForBT(page);
        }
        productCategoryService.selectPage(page);
        return super.packForBT(page);
    }

    /**
     * 新增类别
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProductCategory productCategory) {
        productCategoryService.insert(productCategory);
        return SUCCESS_TIP;
    }

    /**
     * 删除类别
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer productCategoryId) {
        productCategoryService.deleteById(productCategoryId);
        return SUCCESS_TIP;
    }

    /**
     * 修改类别
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ProductCategory productCategory) {
        productCategoryService.updateById(productCategory);
        return SUCCESS_TIP;
    }

    /**
     * 下架类别
     * @param productCategory
     * @return
     */

    @RequestMapping(value = "/remove")
    @ResponseBody
    public Object remove(ProductCategory productCategory) {
        List<Product> products= productService.selectList(new EntityWrapper<Product>().eq("categoryNo", productCategory.getCategoryNo()));
        for (Product product:products){
            return new MyTip(500, product.getProductName()+"还在类别中,不能下架");
        }
        productCategory.setIsDelete("Y");
        productCategoryService.updateById(productCategory);
        return SUCCESS_TIP;
    }

    @RequestMapping(value = "/recovery")
    @ResponseBody
    public Object recovery(ProductCategory productCategory) {
        ProductCategory productCategory1 = productCategoryService.selectById(productCategory.getCategoryNo());
        if (productCategory1.getIsDelete().equals("N")){
            return new MyTip(500,productCategory1.getCategoryName()+"并没有被下架");
        }
        productCategory.setIsDelete("N");
        productCategoryService.updateById(productCategory);
        return SUCCESS_TIP;
    }

    /**
     * 类别详情
     */
    @RequestMapping(value = "/detail/{productCategoryId}")
    @ResponseBody
    public Object detail(@PathVariable("productCategoryId") Integer productCategoryId) {
        return productCategoryService.selectById(productCategoryId);
    }
}
