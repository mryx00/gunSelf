package com.stylefeng.gunSelf.modular.blog.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogCommentTopInfo;
import com.stylefeng.gunSelf.modular.system.model.CommentTop;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-02-26
 */
public interface ICommentTopService extends IService<CommentTop> {
    Page<BlogCommentTopInfo> selectCommentList(Page<BlogCommentTopInfo> page , String account, String title);
}
