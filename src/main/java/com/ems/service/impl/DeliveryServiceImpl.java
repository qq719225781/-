package com.ems.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ems.entity.ApplyInfo;
import com.ems.entity.Delivery;
import com.ems.mapper.ApplyInfoMapper;
import com.ems.mapper.DeliveryMapper;
import com.ems.service.DeliveryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ZJ
 * @data 2023/3/20 21:47
 */
@Service
public class DeliveryServiceImpl extends ServiceImpl<DeliveryMapper, Delivery> implements DeliveryService {

    @Resource
    private DeliveryMapper deliveryMapper;

    @Override
    public IPage pageCC(IPage<Delivery> page, Wrapper wrapper) {
        return deliveryMapper.pageCC(page,wrapper);
    }

}
