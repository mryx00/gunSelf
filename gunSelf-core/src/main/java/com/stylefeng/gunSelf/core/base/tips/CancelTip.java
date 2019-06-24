package com.stylefeng.gunSelf.core.base.tips;

public class CancelTip extends Tip {
    public CancelTip(String text){
        super.code = 500;
        super.message = "操作失败"+text+"存在交接客户";
    }
}
