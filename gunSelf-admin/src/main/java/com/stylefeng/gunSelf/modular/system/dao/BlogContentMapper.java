package com.stylefeng.gunSelf.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogContentInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogContent;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylezhang123
 * @since 2018-09-04
 */
public interface BlogContentMapper extends BaseMapper<BlogContent> {
    List<BlogContentInfo> listContent(Page page, @Param("account") String account,@Param("topic") String topic ,@Param("title")String title,@Param("orderby")String orderby);
}
