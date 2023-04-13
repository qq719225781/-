package com.ems.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.Pakagetype;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ems
 * @since 2023-01-15
 */
@Mapper
public interface PakagetypeMapper extends BaseMapper<Pakagetype> {
    IPage pageCC(IPage<Pakagetype> page, @Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
            com.baomidou.mybatisplus.core.conditions.Wrapper wrapper);

}
