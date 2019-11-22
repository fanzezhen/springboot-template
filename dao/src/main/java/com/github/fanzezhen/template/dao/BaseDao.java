package com.github.fanzezhen.template.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.fanzezhen.template.pojo.entry.BaseEntry;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公共Dao
 */
@Mapper
public interface BaseDao<T extends BaseEntry> extends BaseMapper<T> {
}
