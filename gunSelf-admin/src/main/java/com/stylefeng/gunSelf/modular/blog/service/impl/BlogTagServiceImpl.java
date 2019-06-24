package com.stylefeng.gunSelf.modular.blog.service.impl;

import com.stylefeng.gunSelf.modular.system.model.BlogTag;
import com.stylefeng.gunSelf.modular.system.dao.BlogTagMapper;
import com.stylefeng.gunSelf.modular.blog.service.IBlogTagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-07
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements IBlogTagService {
    @Resource
    BlogTagMapper blogTagMapper;
    public List<BlogTag> selectList2(String account){
        return blogTagMapper.selectList2(account);
    };
}
