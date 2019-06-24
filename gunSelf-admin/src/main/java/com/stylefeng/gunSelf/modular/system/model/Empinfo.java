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
 * @since 2018-07-09
 */
@TableName("empinfo")
public class Empinfo extends Model<Empinfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 员工编号
     */
    @TableId(value = "empNo", type = IdType.AUTO)
    private Integer empNo;
    /**
     * 员工姓名
     */
    private String empName;
    /**
     * 性别
     */
    private String empSex;
    /**
     * 年龄
     */
    private Integer empAge;
    /**
     * 入职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date empDate;
    /**
     * 部门
     */
    private Integer deptId;


    public Integer getEmpNo() {
        return empNo;
    }

    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpSex() {
        return empSex;
    }

    public void setEmpSex(String empSex) {
        this.empSex = empSex;
    }

    public Integer getEmpAge() {
        return empAge;
    }

    public void setEmpAge(Integer empAge) {
        this.empAge = empAge;
    }

    public Date getEmpDate() {
        return empDate;
    }

    public void setEmpDate(Date empDate) {
        this.empDate = empDate;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    @Override
    protected Serializable pkVal() {
        return this.empNo;
    }

    @Override
    public String toString() {
        return "Empinfo{" +
        "empNo=" + empNo +
        ", empName=" + empName +
        ", empSex=" + empSex +
        ", empAge=" + empAge +
        ", empDate=" + empDate +
        ", deptId=" + deptId +
        "}";
    }
}
