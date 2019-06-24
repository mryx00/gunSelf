package com.stylefeng.gunSelf.modular.system.model;


import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-08
 */
@TableName("blog_music")
public class BlogMusic extends Model<BlogMusic> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Excel(name="主键")
    private Integer id;
    /**
     * 音乐名称
     */
    @Excel(name="音乐名称")
    private String musicName;
    /**
     * 账号
     */
    @Excel(name="账号")
    private String account;
    /**
     * 音乐地址
     */
    @Excel(name="音乐地址")
    private String musicAddr;
    /**
     * 是否启用
     */
    @Excel(name="是否启用")
    private Integer isUse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    public String getMusicAddr() {
        return musicAddr;
    }

    public void setMusicAddr(String musicAddr) {
        this.musicAddr = musicAddr;
    }
    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BlogMusic{" +
        "id=" + id +
        ", musicName=" + musicName +
        ", account=" + account +
        ", musicAddr=" + musicAddr +
        ", isUse=" + isUse +
        "}";
    }
}
