package com.ems.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.Warehouse;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ems
 * @since 2023-01-15
 */


public interface WarehouseService extends IService<Warehouse> {
    IPage pageCC(IPage<Warehouse> page, Wrapper wrapper);
}

