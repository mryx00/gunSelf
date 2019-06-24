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
 * @since 2019-05-13
 */
@TableName("blog_backImg")
public class BlogBackimg extends Model<BlogBackimg> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 图片名称
     */
    @Excel(name="图片名称")
    private String imgName;
    /**
     * 图片地址
     */
    @Excel(name="图片地址")
    private String imgAddr;
    /**
     * 是否启用
     */
    @Excel(name="是否启用")
    private Integer isUse;
    /**
     * 所属页面
     */
    @Excel(name="所属页面")
    private Integer belong;
    /**
     * 博客用户
     */
    @Excel(name="博客用户")
    private String blogAccout;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }
    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }
    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }
    public int getBelong() {
        return belong;
    }

    public void setBelong(int belong) {
        this.belong = belong;
    }
    public String getBlogAccout() {
        return blogAccout;
    }

    public void setBlogAccout(String blogAccout) {
        this.blogAccout = blogAccout;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BlogBackimg{" +
        "id=" + id +
        ", imgName=" + imgName +
        ", imgAddr=" + imgAddr +
        ", isUse=" + isUse +
        ", belong=" + belong +
        ", blogAccout=" + blogAccout +
        "}";
    }
}
