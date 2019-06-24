package com.stylefeng.gunSelf.modular.blog.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogTopInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogTop;
import com.stylefeng.gunSelf.modular.system.dao.BlogTopMapper;
import com.stylefeng.gunSelf.modular.blog.service.IBlogTopService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-02-22
 */
@Service
public class BlogTopServiceImpl extends ServiceImpl<BlogTopMapper, BlogTop> implements IBlogTopService {
    @Resource
    private BlogTopMapper blogTopMapper;
    @Override
    public Page<BlogTopInfo> selectTopList(Page<BlogTopInfo> page, String account,String title) {
        return page.setRecords(blogTopMapper.selectTopList(page, account,title));
    }
}
