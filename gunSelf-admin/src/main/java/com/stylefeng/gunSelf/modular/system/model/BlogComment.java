package com.stylefeng.gunSelf.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2018-09-05
 */
@TableName("blog_comment")
public class BlogComment extends Model<BlogComment> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
     * 评论时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    public Date getCtDate() {
        return ctDate;
    }

    public void setCtDate(Date ctDate) {
        this.ctDate = ctDate;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BlogComment{" +
        "id=" + id +
        ", articleId=" + articleId +
        ", ctPersonId=" + ctPersonId +
        ", ctContent=" + ctContent +
        ", ctDate=" + ctDate +
        "}";
    }
}
