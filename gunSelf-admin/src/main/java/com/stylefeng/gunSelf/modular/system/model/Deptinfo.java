package com.stylefeng.gunSelf.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2018-07-10
 */
@TableName("deptinfo")
public class Deptinfo extends Model<Deptinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "deptId", type = IdType.AUTO)
    private Integer deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 部门备注
     */
    private String deptDesp;
    /**
     * 是否删除
     */
    private String isDelete;


    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptDesp() {
        return deptDesp;
    }

    public void setDeptDesp(String deptDesp) {
        this.deptDesp = deptDesp;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.deptId;
    }

    @Override
    public String toString() {
        return "Deptinfo{" +
        "deptId=" + deptId +
        ", deptName=" + deptName +
        ", deptDesp=" + deptDesp +
        ", isDelete=" + isDelete +
        "}";
    }
}
