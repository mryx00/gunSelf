package com.stylefeng.gunSelf.modular.system.model;


import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.format.annotation.DateTimeFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2019-02-26
 */
@TableName("comment_top")
public class CommentTop extends Model<CommentTop> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 评论id
     */
    @Excel(name="评论id")
    private Integer commentId;
    /**
     * 点赞人id
     */
    @Excel(name="点赞人id")
    private Integer tpPersonId;
    /**
     * 点赞日期
     */
    @Excel(name="点赞日期",format="yyyy-MM-dd HH:mm:ss",width=20.0)
    private Date tpDate;

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
        return "CommentTop{" +
        "id=" + id +
        ", commentId=" + commentId +
        ", tpPersonId=" + tpPersonId +
        ", tpDate=" + tpDate +
        "}";
    }
}
