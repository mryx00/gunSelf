package com.stylefeng.gunSelf.modular.system.model;


import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-07
 */
@TableName("blog_photo")
public class BlogPhoto extends Model<BlogPhoto> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 相片名
     */
    @Excel(name="相片名")
    private String photoName;
    /**
     * 相片地址
     */
    @Excel(name="相片地址")
    private String photoAddr;
    /**
     * 排序
     */
    @Excel(name="排序")
    private Integer order;
    /**
     * 用户
     */
    @Excel(name="用户")
    private String account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
    public String getPhotoAddr() {
        return photoAddr;
    }

    public void setPhotoAddr(String photoAddr) {
        this.photoAddr = photoAddr;
    }
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BlogPhoto{" +
        "id=" + id +
        ", photoName=" + photoName +
        ", photoAddr=" + photoAddr +
        ", order=" + order +
        ", account=" + account +
        "}";
    }
}
