package com.ems.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ems.entity.Pakagetype;

import com.ems.mapper.PakagetypeMapper;

import com.ems.service.PakagetypeService;
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
public class PakagetypeServiceImpl extends ServiceImpl<PakagetypeMapper, Pakagetype> implements PakagetypeService {
    @Resource
    private PakagetypeMapper PakagetypeMapper;
    @Override
    public IPage pageCC(IPage<Pakagetype> page, Wrapper wrapper) {

        return PakagetypeMapper.pageCC(page,wrapper);
    }
}
