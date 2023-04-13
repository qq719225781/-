package com.ems.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ems.entity.ApplyInfo;

/**
 * @author ZJ
 * @data 2023/3/20 15:12
 */
public interface ApplyInfoService extends IService<ApplyInfo> {


    IPage pageCC(IPage<ApplyInfo> page, Wrapper wrapper);

    void updateByPakageId(ApplyInfo applyInfo);
}
