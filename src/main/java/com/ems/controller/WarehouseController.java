package com.ems.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.common.QueryPageParam;
import com.ems.common.Result;
import com.ems.entity.Warehouse;
import com.ems.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ems
 * @since 2023-01-15
 */
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;
    //增
    @PostMapping("/save")
    public Result save(@RequestBody Warehouse warehouse){
        return warehouseService.save(warehouse) ? Result.suc() : Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Warehouse warehouse){
        return warehouseService.updateById(warehouse) ? Result.suc() : Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return warehouseService.removeById(id) ? Result.suc() : Result.fail();
    }

    //查询
    @PostMapping("/listPage")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");


        Page<Warehouse> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Warehouse> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Warehouse::getName,name);
        }


        IPage result = warehouseService.pageCC(page,lambdaQueryWrapper);


        return Result.suc(result.getRecords(),result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list = warehouseService.list();
        return Result.suc(list);
    }


}
