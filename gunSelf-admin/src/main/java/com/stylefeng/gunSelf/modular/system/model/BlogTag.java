package com.stylefeng.gunSelf.modular.system.model;


import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import org.springframework.format.annotation.DateTimeFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-26
 */
@TableName("blog_tag")
public class BlogTag extends Model<BlogTag> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 标签名
     */
    @Excel(name="标签名")
    private String tagName;
    /**
     * 计数
     */
    @Excel(name="计数")
    private Integer count;
    /**
     * 创建时间
     */
    @Excel(name="创建时间",format="yyyy-MM-dd HH:mm:ss",width=20.0)
    private Date creatTime;
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
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
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
        return "BlogTag{" +
        "id=" + id +
        ", tagName=" + tagName +
        ", count=" + count +
        ", creatTime=" + creatTime +
        ", account=" + account +
        "}";
    }
}
