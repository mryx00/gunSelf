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
 * @since 2019-05-12
 */
@TableName("blog_board")
public class BlogBoard extends Model<BlogBoard> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 留言内容
     */
    @Excel(name="留言内容")
    private String content;
    /**
     * 用户名
     */
    @Excel(name="用户名")
    private String account;
    /**
     * 所属博客账户
     */
    @Excel(name="所属博客账户")
    private String blogAccount;
    /**
     * 发表时间
     */
    @Excel(name="发表时间",format="yyyy-MM-dd HH:mm:ss",width=20.0)
    private Date creatTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getBlogAccount() {
        return blogAccount;
    }

    public void setBlogAccount(String blogAccount) {
        this.blogAccount = blogAccount;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BlogBoard{" +
        "id=" + id +
        ", content=" + content +
        ", account=" + account +
        ", blogAccount=" + blogAccount +
        ", creatTime=" + creatTime +
        "}";
    }
}
