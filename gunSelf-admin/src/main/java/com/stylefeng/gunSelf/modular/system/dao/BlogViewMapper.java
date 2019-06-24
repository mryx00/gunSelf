package com.stylefeng.gunSelf.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogViewInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogView;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylezhang123
 * @since 2019-02-22
 */
public interface BlogViewMapper extends BaseMapper<BlogView> {
    List<BlogViewInfo> selectViewList(Page page,  @Param("account") String account,@Param("title") String title);
}
