package com.stylefeng.gunSelf.modular.system.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author stylezhang123
 * @since 2018-07-11
 */
@TableName("orderitem")
public class Orderitem extends Model<Orderitem> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    @TableId(value = "orderNo", type = IdType.AUTO)
    private Integer orderNo;
    /**
     * 客户姓名
     */
    private Integer customerNo;
    /**
     * 下单员工
     */
    private Integer employeeNo;
    /**
     * 下单时间
     */
    private Date orderTime;
    /**
     * 订单状态
     */
    private Integer orderState;
    /**
     * 产品数量
     */
    private Integer protectNumber;


    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Integer customerNo) {
        this.customerNo = customerNo;
    }

    public Integer getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(Integer employeeNo) {
        this.employeeNo = employeeNo;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public Integer getProtectNumber() {
        return protectNumber;
    }

    public void setProtectNumber(Integer protectNumber) {
        this.protectNumber = protectNumber;
    }

    @Override
    protected Serializable pkVal() {
        return this.orderNo;
    }

    @Override
    public String toString() {
        return "Orderitem{" +
        "orderNo=" + orderNo +
        ", customerNo=" + customerNo +
        ", employeeNo=" + employeeNo +
        ", orderTime=" + orderTime +
        ", orderState=" + orderState +
        ", protectNumber=" + protectNumber +
        "}";
    }
}
