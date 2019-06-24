package com.stylefeng.gunSelf.modular.blog.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogCommentTopInfo;
import com.stylefeng.gunSelf.modular.system.model.CommentTop;
import com.stylefeng.gunSelf.modular.system.dao.CommentTopMapper;
import com.stylefeng.gunSelf.modular.blog.service.ICommentTopService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-02-26
 */
@Service
public class CommentTopServiceImpl extends ServiceImpl<CommentTopMapper, CommentTop> implements ICommentTopService {
    @Resource
    CommentTopMapper commentTopMapper;
    @Override
    public Page<BlogCommentTopInfo> selectCommentList(Page<BlogCommentTopInfo> page, String account, String title) {
        return page.setRecords(commentTopMapper.selectTopList(page,account,title));
    }
}
