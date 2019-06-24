package com.stylefeng.gunSelf.modular.system.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.gunSelf.modular.blog.vo.BlogCommentInfo;
import com.stylefeng.gunSelf.modular.system.model.BlogComment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylezhang123
 * @since 2018-09-05
 */
public interface BlogCommentMapper extends BaseMapper<BlogComment> {
    List<BlogCommentInfo>  listComment(@Param("id")int id, @Param("current") int current, @Param("size") int size,@Param("orderby") String orderby);

    List<BlogCommentInfo> selectCommentList(Page page, @Param("account")String account,@Param("title") String title);
}
