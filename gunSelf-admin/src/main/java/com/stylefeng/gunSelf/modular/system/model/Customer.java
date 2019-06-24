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
 * @since 2018-07-11
 */
@TableName("customer")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "customerNo", type = IdType.AUTO)
    private Integer customerNo;
    /**
     * 客户姓名
     */
    private String customerName;
    /**
     * 联系方式
     */
    private String contactInformation;
    /**
     * 客户地址
     */
    private String address;
    /**
     * 所属员工
     */
    private Integer belongUser;
    /**
     * 是否删除
     */
    private String isDelete;


    public Integer getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(Integer customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getBelongUser() {
        return belongUser;
    }

    public void setBelongUser(Integer belongUser) {
        this.belongUser = belongUser;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    protected Serializable pkVal() {
        return this.customerNo;
    }

    @Override
    public String toString() {
        return "Customer{" +
        "customerNo=" + customerNo +
        ", customerName=" + customerName +
        ", contactInformation=" + contactInformation +
        ", address=" + address +
        ", belongUser=" + belongUser +
        ", isDelete=" + isDelete +
        "}";
    }
}
