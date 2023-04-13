package com.ems.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.Pakage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ems.entity.Warehouse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ems
 * @since 2023-01-15
 */
public interface PakageService extends IService<Pakage> {
    IPage pageCC(IPage<Pakage> page, Wrapper wrapper);
}
