package com.ems.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.common.QueryPageParam;
import com.ems.common.Result;
import com.ems.entity.ApplyInfo;
import com.ems.entity.Pakage;
import com.ems.service.PakageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ems
 * @since 2023-01-15
 */
@RestController
@RequestMapping("/pakage")
public class PakageController {
    @Autowired
    private PakageService pakageService;

    //增
    @PostMapping("/save")
    public Result save(@RequestBody Pakage pakage) {
        pakage.setIsDelete("0");
        return pakageService.save(pakage) ? Result.suc() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Pakage pakage) {
        return pakageService.updateById(pakage) ? Result.suc() : Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return pakageService.removeById(id) ? Result.suc() : Result.fail();
    }

    //查询
    @PostMapping("/listPage")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();

        Integer biao = null;
        if (!ObjectUtils.isEmpty(param.get("biao"))){
            biao = (Integer) param.get("biao");
        }
        String name = (String) param.get("name");
        String pakagetype = (String) param.get("pakagetype");
        String warehouse = (String) param.get("warehouse");

        Page<Pakage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Pakage> lambdaQueryWrapper = new LambdaQueryWrapper();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(Pakage::getName, name);
        }
        if (StringUtils.isNotBlank(pakagetype) && !"null".equals(pakagetype)) {
            lambdaQueryWrapper.eq(Pakage::getPakagetype, pakagetype);
        }
        if (StringUtils.isNotBlank(warehouse) && !"null".equals(warehouse)) {
            lambdaQueryWrapper.eq(Pakage::getWarehouse, warehouse);
        }
        if (!ObjectUtils.isNull(biao)) {
            lambdaQueryWrapper.eq(Pakage::getUserId, biao);
        }
        lambdaQueryWrapper.eq(Pakage::getIsDelete,"0");
        IPage result = pakageService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }

}
