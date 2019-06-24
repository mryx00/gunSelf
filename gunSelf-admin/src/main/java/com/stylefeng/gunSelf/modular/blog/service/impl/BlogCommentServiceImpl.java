package com.stylefeng.gunSelf.modular.blog.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogCommentInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogComment;
import com.stylefeng.gunSelf.modular.system.dao.BlogCommentMapper;
import com.stylefeng.gunSelf.modular.blog.service.IBlogCommentService;
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
 * @since 2018-09-05
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements IBlogCommentService {

    @Resource
    private BlogCommentMapper blogCommentMapper;
    @Override
    public List<BlogCommentInfo> selectByArticleId(int articleId,int current,int size,String orderby) {
        return blogCommentMapper.listComment(articleId,(current-1)*size,size,orderby);
    }

    @Override
    public Page<BlogCommentInfo> selectCommentList(Page<BlogCommentInfo> page, String account, String title) {
        return page.setRecords(blogCommentMapper.selectCommentList(page,account,title));
    }

}
