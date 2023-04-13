package com.ems.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ems
 * @since 2022-11-30
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    IPage pageC(IPage<User> page);

    IPage pageCC(IPage<User> page, @Param(com.baomidou.mybatisplus.core.toolkit.Constants.WRAPPER)
            com.baomidou.mybatisplus.core.conditions.Wrapper wrapper);

}
