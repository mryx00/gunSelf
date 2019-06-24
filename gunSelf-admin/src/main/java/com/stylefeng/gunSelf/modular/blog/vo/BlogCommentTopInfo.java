package com.stylefeng.gunSelf.modular.blog.vo;

import java.util.Date;

public class BlogCommentTopInfo {
    private Integer id;
    /**
     * 评论id
     */

    private Integer commentId;
    /**
     * 评论人id
     */

    private Integer tpPersonId;

    /**
     * 点赞日期
     */

    private Date tpDate;

    private String account;

    private String caption;

    private String ctContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getTpPersonId() {
        return tpPersonId;
    }

    public void setTpPersonId(Integer tpPersonId) {
        this.tpPersonId = tpPersonId;
    }

    public Date getTpDate() {
        return tpDate;
    }

    public void setTpDate(Date tpDate) {
        this.tpDate = tpDate;
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

    public String getCtContent() {
        return ctContent;
    }

    public void setCtContent(String ctContent) {
        this.ctContent = ctContent;
    }
}
