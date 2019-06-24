package com.stylefeng.gunSelf.modular.blog.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogViewInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogView;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author stylezhang123
 * @since 2019-02-22
 */
public interface IBlogViewService extends IService<BlogView> {

    Page<BlogViewInfo> selectViewList(Page<BlogViewInfo> page , @Param("account") String account,@Param("title") String title);

}
