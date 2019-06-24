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
 * @since 2019-05-07
 */
@TableName("blog_link")
public class BlogLink extends Model<BlogLink> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 链接名
     */
    @Excel(name="链接名")
    private String linkName;
    /**
     * 外链接地址
     */
    @Excel(name="外链接地址")
    private String linkAddr;
    /**
     * 账户名
     */
    @Excel(name="账户名")
    private String account;
    /**
     * 点击次数
     */
    @Excel(name="点击次数")
    private Integer count;
    /**
     * 创建时间
     */
    @Excel(name="创建时间",format="yyyy-MM-dd HH:mm:ss",width=20.0)
    private Date createTime;
    /**
     * 排序
     */
    @Excel(name="排序")
    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
    public String getLinkAddr() {
        return linkAddr;
    }

    public void setLinkAddr(String linkAddr) {
        this.linkAddr = linkAddr;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BlogLink{" +
        "id=" + id +
        ", linkName=" + linkName +
        ", linkAddr=" + linkAddr +
        ", account=" + account +
        ", count=" + count +
        ", createTime=" + createTime +
        ", order=" + order +
        "}";
    }
}
