package com.ems.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.ApplyInfo;
import com.ems.entity.Delivery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZJ
 * @data 2023/3/20 21:48
 */
@Mapper
public interface DeliveryMapper extends BaseMapper<Delivery> {


    IPage pageCC(IPage<Delivery> page, @Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
            com.baomidou.mybatisplus.core.conditions.Wrapper wrapper);
}
