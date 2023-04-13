package com.ems.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.Pakage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ems.entity.Warehouse;
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
public interface PakageMapper extends BaseMapper<Pakage> {
    IPage pageCC(IPage<Pakage> page, @Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
            com.baomidou.mybatisplus.core.conditions.Wrapper wrapper);
}
