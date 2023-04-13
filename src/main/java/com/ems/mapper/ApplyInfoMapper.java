package com.ems.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.ApplyInfo;
import com.ems.entity.Pakage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author ZJ
 * @data 2023/3/20 15:23
 */
@Mapper
public interface ApplyInfoMapper extends BaseMapper<ApplyInfo> {

    IPage pageCC(IPage<ApplyInfo> page, @Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
            com.baomidou.mybatisplus.core.conditions.Wrapper wrapper);

    void updateByPakageId(ApplyInfo applyInfo);
}
