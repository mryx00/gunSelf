package com.stylefeng.gunSelf.modular.blog.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogBoardInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogBoard;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-12
 */
public interface IBlogBoardService extends IService<BlogBoard> {
    Page<BlogBoardInfo> selectBlogBoardList(Page<BlogBoardInfo> page ,String blogAccount);
}
