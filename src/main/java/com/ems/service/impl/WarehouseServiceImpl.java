package com.ems.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.User;
import com.ems.entity.Warehouse;
import com.ems.mapper.UserMapper;
import com.ems.mapper.WarehouseMapper;
import com.ems.service.WarehouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ems
 * @since 2023-01-15
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {

    @Resource
    private WarehouseMapper warehouseMapper;
    @Override
    public IPage pageCC(IPage<Warehouse> page, Wrapper wrapper) {

        return warehouseMapper.pageCC(page,wrapper);
    }

}
