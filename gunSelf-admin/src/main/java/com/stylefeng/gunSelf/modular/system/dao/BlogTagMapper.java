package com.stylefeng.gunSelf.modular.system.dao;

import com.stylefeng.gunSelf.modular.system.model.BlogTag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author stylezhang123
 * @since 2019-05-07
 */
public interface BlogTagMapper extends BaseMapper<BlogTag> {
        List<BlogTag> selectList2(@Param("account") String account);
}
