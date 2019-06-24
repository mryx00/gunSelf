package com.stylefeng.gunSelf.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2018-07-21
 */
@TableName("deploymodel")
public class Deploymodel extends Model<Deploymodel> {

    private static final long serialVersionUID = 1L;

    /**
     * 部署Id
     */
    private Integer deployId;
    /**
     * 部署名称
     */
    private String deployName;
    /**
     * 部署时间
     */
    private String deployTime;


    public Integer getDeployId() {
        return deployId;
    }

    public void setDeployId(Integer deployId) {
        this.deployId = deployId;
    }

    public String getDeployName() {
        return deployName;
    }

    public void setDeployName(String deployName) {
        this.deployName = deployName;
    }

    public String getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(String deployTime) {
        this.deployTime = deployTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.deployId;
    }

    @Override
    public String toString() {
        return "Deploymodel{" +
        "deployId=" + deployId +
        ", deployName=" + deployName +
        ", deployTime=" + deployTime +
        "}";
    }
}
