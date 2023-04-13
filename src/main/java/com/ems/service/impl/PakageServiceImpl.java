package com.ems.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.Pakage;
import com.ems.entity.Warehouse;
import com.ems.mapper.PakageMapper;
import com.ems.service.PakageService;
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
public class PakageServiceImpl extends ServiceImpl<PakageMapper, Pakage> implements PakageService {

    @Resource
    private PakageMapper pakageMapper;
    @Override
    public IPage pageCC(IPage<Pakage> page, Wrapper wrapper) {
        return pakageMapper.pageCC(page,wrapper);
    }
}
