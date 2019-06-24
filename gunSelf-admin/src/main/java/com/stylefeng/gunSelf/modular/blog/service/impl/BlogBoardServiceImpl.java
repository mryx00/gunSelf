package com.stylefeng.gunSelf.modular.blog.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogBoardInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogBoard;
import com.stylefeng.gunSelf.modular.system.dao.BlogBoardMapper;
import com.stylefeng.gunSelf.modular.blog.service.IBlogBoardService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-12
 */
@Service
public class BlogBoardServiceImpl extends ServiceImpl<BlogBoardMapper, BlogBoard> implements IBlogBoardService {

    @Resource
    BlogBoardMapper blogBoardMapper;
    @Override
    public Page<BlogBoardInfo> selectBlogBoardList(Page<BlogBoardInfo> page, String blogAccount) {
        return  page.setRecords(blogBoardMapper.selectBlogBoardList(page,blogAccount));
    }
}
