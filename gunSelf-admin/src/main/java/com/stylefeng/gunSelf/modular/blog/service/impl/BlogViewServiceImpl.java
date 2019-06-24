package com.stylefeng.gunSelf.modular.blog.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogViewInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogView;
import com.stylefeng.gunSelf.modular.system.dao.BlogViewMapper;
import com.stylefeng.gunSelf.modular.blog.service.IBlogViewService;
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
public class BlogViewServiceImpl extends ServiceImpl<BlogViewMapper, BlogView> implements IBlogViewService {

    @Resource
    BlogViewMapper blogViewMapper;

    @Override
    public Page<BlogViewInfo> selectViewList(Page<BlogViewInfo> page, String account, String title) {
        return page.setRecords(blogViewMapper.selectViewList(page,account,title));
    }
}
