package com.stylefeng.gunSelf.modular.SX.wrapper;

import com.stylefeng.gunSelf.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.gunSelf.core.common.constant.factory.ConstantFactory;

import java.util.Map;

public class CustomerWrapper extends BaseControllerWarpper {
    public CustomerWrapper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.put("belongUser", ConstantFactory.me().getUserNameById((Integer) map.get("belongUser")));
    }
}
