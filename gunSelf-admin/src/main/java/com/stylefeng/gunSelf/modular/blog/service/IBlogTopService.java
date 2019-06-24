package com.stylefeng.gunSelf.modular.blog.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogTopInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogTop;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-02-22
 */
public interface IBlogTopService extends IService<BlogTop> {
    Page<BlogTopInfo> selectTopList(Page<BlogTopInfo> page , String account,String title);


}
