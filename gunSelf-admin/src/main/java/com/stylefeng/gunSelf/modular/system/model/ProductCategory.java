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
 * @since 2018-07-10
 */
@TableName("product_category")
public class ProductCategory extends Model<ProductCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "categoryNo", type = IdType.AUTO)
    private Integer categoryNo;
    /**
     * 类别名称
     */
    private String categoryName;
    /**
     * 类别备注
     */
    private String description;
    /**
     * 是否下架
     */
    private String isDelete;


    public Integer getCategoryNo() {
        return categoryNo;
    }

    public void setCategoryNo(Integer categoryNo) {
        this.categoryNo = categoryNo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.categoryNo;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
        "categoryNo=" + categoryNo +
        ", categoryName=" + categoryName +
        ", description=" + description +
        ", isDelete=" + isDelete +
        "}";
    }
}
