package com.stylefeng.gunSelf.modular.blog.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogCommentInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogComment;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylezhang123
 * @since 2018-09-02
 */
public interface IBlogCommentService extends IService<BlogComment> {
        List<BlogCommentInfo> selectByArticleId(int articleId,int current,int size,String orderby);

        Page<BlogCommentInfo> selectCommentList(Page<BlogCommentInfo> page , String account, String title);
}
