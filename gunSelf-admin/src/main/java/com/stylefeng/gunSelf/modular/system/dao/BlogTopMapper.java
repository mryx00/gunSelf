package com.stylefeng.gunSelf.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogTopInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogTop;
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
public interface BlogTopMapper extends BaseMapper<BlogTop> {
    List<BlogTopInfo> selectTopList(Page page, @Param("account") String account,@Param("title") String title);


}
