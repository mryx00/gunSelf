package com.stylefeng.gunSelf.modular.SX.wrapper;

import com.stylefeng.gunSelf.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.gunSelf.core.common.constant.factory.ConstantFactory;

import java.util.Map;

public class ProductWrapper extends BaseControllerWarpper
{

    public ProductWrapper(Object list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {
            map.put("productPlace", ConstantFactory.me().getDictName((Integer) map.get("productPlace")));
            map.put("productSpecs",ConstantFactory.me().getDictName((Integer) map.get("productSpecs")));
            map.put("productYear",ConstantFactory.me().getDictName((Integer) map.get("productYear")));
            map.put("categoryNo",ConstantFactory.me().getProductCategoryName((Integer) map.get("categoryNo")));
    }
}
