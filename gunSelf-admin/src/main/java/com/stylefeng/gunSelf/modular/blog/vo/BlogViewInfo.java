package com.stylefeng.gunSelf.modular.blog.vo;

import java.util.Date;

public class BlogViewInfo {

    private Integer id;
    /**
     * 浏览人id
     */

    private Integer viewPersonId;
    /**
     * 文章Id
     */

    private Integer articleId;

    /**
     * 查看时间
     */

    private Date viewDate;

    private String account;

    private String caption;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getViewPersonId() {
        return viewPersonId;
    }

    public void setViewPersonId(Integer viewPersonId) {
        this.viewPersonId = viewPersonId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


}
