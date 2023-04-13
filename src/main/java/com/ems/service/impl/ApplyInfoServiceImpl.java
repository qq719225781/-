package com.ems.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ems.entity.ApplyInfo;
import com.ems.entity.Pakage;
import com.ems.mapper.ApplyInfoMapper;
import com.ems.mapper.PakageMapper;
import com.ems.service.ApplyInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ZJ
 * @data 2023/3/20 15:12
 */
@Service
public class ApplyInfoServiceImpl extends ServiceImpl<ApplyInfoMapper, ApplyInfo> implements ApplyInfoService {

    @Resource
    private ApplyInfoMapper applyInfoMapper;

    @Override
    public IPage pageCC(IPage<ApplyInfo> page, Wrapper wrapper) {
        return applyInfoMapper.pageCC(page,wrapper);
    }

    @Override
    public void updateByPakageId(ApplyInfo applyInfo) {
        applyInfoMapper.updateByPakageId(applyInfo);
    }


}
