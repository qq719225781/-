package com.ems.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.Pakage;
import com.ems.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ems
 * @since 2023-01-16
 */

@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    IPage pageCC(IPage<Record> page, @Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
            com.baomidou.mybatisplus.core.conditions.Wrapper wrapper);
}
