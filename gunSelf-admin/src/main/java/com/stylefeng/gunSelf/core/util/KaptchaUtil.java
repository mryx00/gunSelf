package com.stylefeng.gunSelf.core.util;

import com.stylefeng.gunSelf.config.properties.FadpProperties;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(FadpProperties.class).getKaptchaOpen();
    }
}