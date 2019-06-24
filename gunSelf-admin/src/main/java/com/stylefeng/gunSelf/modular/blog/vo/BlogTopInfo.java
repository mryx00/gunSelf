package com.stylefeng.gunSelf.modular.blog.vo;


import java.util.Date;

public class BlogTopInfo {

    int id;
    /**
     * 文章Id
     */
    int articleId;
    /**
     * 点赞人Id
     */
    int tpPersonId;
    /**
     * 点赞时间
     */
    Date tpDate;

    String caption;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    String account;

    String tpName;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getTpPersonId() {
        return tpPersonId;
    }

    public void setTpPersonId(int tpPersonId) {
        this.tpPersonId = tpPersonId;
    }

    public Date getTpDate() {
        return tpDate;
    }

    public void setTpDate(Date tpDate) {
        this.tpDate = tpDate;
    }


    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

}
