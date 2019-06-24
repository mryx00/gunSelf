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
@TableName("activitimodel")
public class Activitimodel extends Model<Activitimodel> {

    private static final long serialVersionUID = 1L;

    /**
     * 模型编号
     */

    private Integer modelId;
    /**
     * 模型名称
     */
    private String modelName;
    /**
     * 版本
     */
    private String version;
    /**
     * 创建时间
     */
    private String createTime;


    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.modelId;
    }

    @Override
    public String toString() {
        return "Activitimodel{" +
        "modelId=" + modelId +
        ", modelName=" + modelName +
        ", version=" + version +
        ", createTime=" + createTime +
        "}";
    }
}
