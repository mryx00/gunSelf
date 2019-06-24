package com.stylefeng.gunSelf.modular.SX.wrapper;

import com.stylefeng.gunSelf.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.gunSelf.core.common.constant.factory.ConstantFactory;

import java.util.Map;

public class InStockWrapper extends BaseControllerWarpper
{

    public InStockWrapper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
        map.put("productId", ConstantFactory.me().getProductName((Integer) map.get("productId")));
        map.put("userId", ConstantFactory.me().getUserNameById((Integer) map.get("userId")));
    }
}
