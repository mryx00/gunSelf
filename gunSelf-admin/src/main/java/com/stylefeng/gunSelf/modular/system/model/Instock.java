package com.stylefeng.gunSelf.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2018-07-11
 */
@TableName("instock")
public class Instock extends Model<Instock> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "instockId", type = IdType.AUTO)
    private Integer instockId;
    /**
     * 产品名称
     */
    private Integer productId;
    /**
     * 入库申请人
     */
    private Integer userId;
    /**
     * 入库备注
     */
    private String description;
    /**
     * 入库数量
     */
    private Integer instockNum;
    /**
     * 入库时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date instockTime;


    public Integer getInstockId() {
        return instockId;
    }

    public void setInstockId(Integer instockId) {
        this.instockId = instockId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInstockNum() {
        return instockNum;
    }

    public void setInstockNum(Integer instockNum) {
        this.instockNum = instockNum;
    }

    public Date getInstockTime() {
        return instockTime;
    }

    public void setInstockTime(Date instockTime) {
        this.instockTime = instockTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.instockId;
    }

    @Override
    public String toString() {
        return "Instock{" +
        "instockId=" + instockId +
        ", productId=" + productId +
        ", userId=" + userId +
        ", description=" + description +
        ", instockNum=" + instockNum +
        ", instockTime=" + instockTime +
        "}";
    }
}
