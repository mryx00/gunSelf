package com.stylefeng.gunSelf.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogBoardInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogBoard;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-12
 */
public interface BlogBoardMapper extends BaseMapper<BlogBoard> {
    List<BlogBoardInfo> selectBlogBoardList(Page<BlogBoardInfo> page , @Param("blogAccount") String blogAccount);
}
