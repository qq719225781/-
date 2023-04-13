package com.ems.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.common.QueryPageParam;
import com.ems.common.Result;
import com.ems.entity.Pakagetype;

import com.ems.service.PakagetypeService;

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
@RequestMapping("/pakagetype")
public class PakagetypeController {
    @Autowired
    private PakagetypeService pakagetypeService;
    //增
    @PostMapping("/save")
    public Result save(@RequestBody Pakagetype pakagetype){
        return pakagetypeService.save(pakagetype) ? Result.suc() : Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Pakagetype pakagetype){
        return pakagetypeService.updateById(pakagetype) ? Result.suc() : Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return pakagetypeService.removeById(id) ? Result.suc() : Result.fail();
    }

    //查询
    @PostMapping("/listPage")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");


        Page<Pakagetype> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Pakagetype> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Pakagetype::getName,name);
        }


        IPage result = pakagetypeService.pageCC(page,lambdaQueryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }
    @GetMapping("/list")
    public Result list(){
        List list = pakagetypeService.list();
        return Result.suc(list);
    }

}
