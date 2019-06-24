package com.stylefeng.gunSelf.modular.blog.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import java.util.Date;
@ExcelTarget("BlogTopEntity")
public class BlogTopEntity implements java.io.Serializable {
    private Integer id;

    /**
     * 文章Id
     */
    @Excel(name = "文章Id")
    private Integer articleId;
    /**
     * 点赞人Id
     */
    @Excel(name = "点赞人")
    private Integer tpPersonId;
    /**
     * 点赞时间
     */
    @Excel(name = "点赞日期 ")
    private Date tpDate;

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



 }