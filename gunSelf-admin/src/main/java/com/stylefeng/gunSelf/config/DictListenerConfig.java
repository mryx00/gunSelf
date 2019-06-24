package com.stylefeng.gunSelf.config;

import com.stylefeng.gunSelf.core.cache.CacheKit;
import com.stylefeng.gunSelf.modular.system.dao.DictMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * 初始化字典到缓存中
 */
@Configuration
public class DictListenerConfig implements InitializingBean {
    @Autowired
    private DictMapper dictMapper;
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Map<String, Object>> maps = dictMapper.listCache();
        System.out.println(maps);
        CacheKit.put("CONSTANT","dictCache",dictMapper.listCache());
    }
}
