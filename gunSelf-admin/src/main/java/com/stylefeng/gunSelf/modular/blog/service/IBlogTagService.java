package com.stylefeng.gunSelf.modular.blog.service;

import com.stylefeng.gunSelf.modular.system.model.BlogTag;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-07
 */
public interface IBlogTagService extends IService<BlogTag> {
     List<BlogTag> selectList2(String account);
}
