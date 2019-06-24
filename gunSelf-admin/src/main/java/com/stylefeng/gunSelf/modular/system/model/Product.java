package com.stylefeng.gunSelf.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2018-07-11
 */
@TableName("product")
public class Product extends Model<Product> {

    private static final long serialVersionUID = 1L;

    /**
     * 产品编号
     */
    @TableId(value = "productNo", type = IdType.AUTO)
    private Integer productNo;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产地
     */
    private Integer productPlace;
    /**
     * 产品规格
     */
    private Integer productSpecs;
    /**
     * 价格(元)
     */
    private Integer productPrice;
    /**
     * 产品年份
     */
    private Integer productYear;
    /**
     * 库存数
     */
    private Integer stockNumber;
    /**
     * 产品类别
     */
    private Integer categoryNo;
    /**
     * 是否下架
     */
    private String isDelete;


    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductPlace() {
        return productPlace;
    }

    public void setProductPlace(Integer productPlace) {
        this.productPlace = productPlace;
    }

    public Integer getProductSpecs() {
        return productSpecs;
    }

    public void setProductSpecs(Integer productSpecs) {
        this.productSpecs = productSpecs;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductYear() {
        return productYear;
    }

    public void setProductYear(Integer productYear) {
        this.productYear = productYear;
    }

    public Integer getStockNumber() {
        return stockNumber;
    }

    public void setStockNumber(Integer stockNumber) {
        this.stockNumber = stockNumber;
    }

    public Integer getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(Integer categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.productNo;
    }

    @Override
    public String toString() {
        return "Product{" +
        "productNo=" + productNo +
        ", productName=" + productName +
        ", productPlace=" + productPlace +
        ", productSpecs=" + productSpecs +
        ", productPrice=" + productPrice +
        ", productYear=" + productYear +
        ", stockNumber=" + stockNumber +
        ", categoryNo=" + categoryNo +
        ", isDelete=" + isDelete +
        "}";
    }
}
