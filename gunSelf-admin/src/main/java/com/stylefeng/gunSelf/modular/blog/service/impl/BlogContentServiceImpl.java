package com.stylefeng.gunSelf.modular.blog.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogContentInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogContent;
import com.stylefeng.gunSelf.modular.system.dao.BlogContentMapper;
import com.stylefeng.gunSelf.modular.blog.service.IBlogContentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylezhang123
 * @since 2018-09-04
 */
@Service
public class BlogContentServiceImpl extends ServiceImpl<BlogContentMapper, BlogContent> implements IBlogContentService {
        @Resource
        private BlogContentMapper blogContentMapper;
        @Override
        public Page<BlogContentInfo> selectByArticleId(Page<BlogContentInfo> page, String  account, String topic,String title,String orderby) {
            return page.setRecords(blogContentMapper.listContent(page,account,topic,title,orderby));
        }

}
