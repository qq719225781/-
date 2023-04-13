package com.ems.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ems.entity.ApplyInfo;
import com.ems.entity.Delivery;

/**
 * @author ZJ
 * @data 2023/3/20 21:47
 */
public interface DeliveryService extends IService<Delivery> {

    IPage pageCC(IPage<Delivery> page, Wrapper wrapper);
}
