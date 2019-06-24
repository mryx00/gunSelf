package com.stylefeng.gunSelf.modular.SX.wrapper;

import com.stylefeng.gunSelf.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.gunSelf.core.common.constant.factory.ConstantFactory;

import java.util.Map;

public class OrderItemWrapper  extends BaseControllerWarpper {
    public OrderItemWrapper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.put("customerNo", ConstantFactory.me().getCustomerName((Integer) map.get("customerNo")));
        map.put("employeeNo",ConstantFactory.me().getUserNameById((Integer) map.get("employeeNo")));

    }
}
