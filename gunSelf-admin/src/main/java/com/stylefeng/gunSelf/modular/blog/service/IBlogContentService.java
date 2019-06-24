package com.stylefeng.gunSelf.modular.blog.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogContentInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogContent;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylezhang123
 * @since 2018-09-04
 */
public interface IBlogContentService extends IService<BlogContent> {
    public Page<BlogContentInfo> selectByArticleId(Page<BlogContentInfo> page, String account, String topic,String title,String orderby);

}
