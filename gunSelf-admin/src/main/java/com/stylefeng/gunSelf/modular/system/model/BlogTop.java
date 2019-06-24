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
@TableName("blog_top")
public class BlogTop extends Model<BlogTop> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 文章Id
     */
    @Excel(name="文章Id")
    private Integer articleId;
    /**
     * 点赞人Id
     */
    @Excel(name="点赞人Id")
    private Integer tpPersonId;
    /**
     * 点赞时间
     */
    @Excel(name="点赞时间",format="yyyy-MM-dd HH:mm:ss",width=20.0)
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getTpDate() {
        return tpDate;
    }

    public void setTpDate(Date tpDate) {
        this.tpDate = tpDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BlogTop{" +
        "id=" + id +
        ", articleId=" + articleId +
        ", tpPersonId=" + tpPersonId +
        ", tpDate=" + tpDate +
        "}";
    }
}
