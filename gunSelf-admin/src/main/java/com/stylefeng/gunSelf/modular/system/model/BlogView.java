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
 * @since 2019-02-22
 */
@TableName("blog_view")
public class BlogView extends Model<BlogView> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 浏览人id
     */
    @Excel(name="浏览人id")
    private Integer viewPersonId;
    /**
     * 文章Id
     */
    @Excel(name="文章Id")
    private Integer articleId;
    /**
     * 查看时间
     */
    @Excel(name="查看时间",format="yyyy-MM-dd HH:mm:ss",width=20.0)
    private Date viewDate;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getViewDate() {
        return viewDate;
    }

    public void setViewDate(Date viewDate) {
        this.viewDate = viewDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BlogView{" +
        "id=" + id +
        ", viewPersonId=" + viewPersonId +
        ", articleId=" + articleId +
        ", viewDate=" + viewDate +
        "}";
    }
}
