package com.stylefeng.gunSelf.modular.blog.vo;

import java.util.Date;

public class BlogCommentInfo {
    private Integer id;
    /**
     * 文章Id
     */
    private Integer articleId;


    /**
     * 评论人
     */
    private Integer ctPersonId;
    /**
     * 评论内容
     */
    private String ctContent;

    /**
     * 点赞次数
     */
    private int topCount;

    private String account;

    private String caption;



    private String ctPersonName;

    public Date getCtDate() {
        return ctDate;
    }

    public void setCtDate(Date ctDate) {
        this.ctDate = ctDate;
    }

    private String ctPersonAvatar;

    private Date ctDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getCtPersonId() {
        return ctPersonId;
    }

    public void setCtPersonId(Integer ctPersonId) {
        this.ctPersonId = ctPersonId;
    }

    public String getCtContent() {
        return ctContent;
    }

    public void setCtContent(String ctContent) {
        this.ctContent = ctContent;
    }

    public String getCtPersonName() {
        return ctPersonName;
    }

    public void setCtPersonName(String ctPersonName) {
        this.ctPersonName = ctPersonName;
    }

    public String getCtPersonAvatar() {
        return ctPersonAvatar;
    }

    public void setCtPersonAvatar(String ctPersonAvatar) {
        this.ctPersonAvatar = ctPersonAvatar;
    }

    public int getTopCount() {
        return topCount;
    }

    public void setTopCount(int topCount) {
        this.topCount = topCount;
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
