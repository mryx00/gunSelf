package com.stylefeng.gunSelf.core.util;

import com.stylefeng.gunSelf.core.cache.CacheKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 */
public class DictUtil {
    public static List<Map<String,Object>> getDictByKey(String key){
        boolean flag = false;
        List<Map<String,Object>> dict = new ArrayList<>();
        List<Map<String,Object>> list = CacheKit.get("CONSTANT","dictCache");
        for (Map<String,Object> m :list){
            if (key.equals((String)m.get("name"))){
                dict.add(m);
                flag = true;
            }
            else {
                if (flag){
                return dict;
                }
            }
        }
        return dict;
    }
}
