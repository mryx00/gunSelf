package com.stylefeng.gunSelf.modular.blog.vo;

import java.util.Date;

public class BlogBoardInfo {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 留言内容
     */
    private String content;
    /**
     * 用户名
     */
    private String account;
    /**
     * 所属博客账户
     */
    private String blogAccount;
    /**
     * 发表时间
     */
    private Date creatTime;

    private String name;

    private String avatar;

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

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
