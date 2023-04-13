package com.ems.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.common.QueryPageParam;
import com.ems.common.Result;
import com.ems.entity.ApplyInfo;
import com.ems.entity.Pakage;
import com.ems.entity.Record;
import com.ems.service.ApplyInfoService;
import com.ems.service.PakageService;
import com.ems.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ems
 * @since 2023-01-16
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private PakageService pakageService;

    @Autowired
    private ApplyInfoService applyInfoService;

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String pakagetype = (String)param.get("pakagetype");
        String warehouse = (String)param.get("warehouse");
        String roleId = (String)param.get("roleId");

        String userId = (String)param.get("userId");
        if(userId.equals("1")){

        }

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.apply(" a.pakage=b.id and b.warehouse=c.id and b.pakagetype=d.id ");

        if("2".equals(roleId.toString())){
            queryWrapper.apply(" a.userId= "+userId);
        }
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like("b.name",name);
        }
        if(StringUtils.isNotBlank(pakagetype) && !"null".equals(pakagetype)){
            queryWrapper.eq("d.id",pakagetype);

        }
        if(StringUtils.isNotBlank(warehouse) && !"null".equals(warehouse)){
            queryWrapper.eq("c.id",warehouse);
        }


        IPage result = recordService.pageCC(page,queryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }


    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record){

        Pakage pakage = pakageService.getById(record.getPakage());
        pakage.setIsDelete("1");
        int n = record.getCount();
        if("2".equals(record.getAction())){
            record.setCount(n);
        }

        pakageService.updateById(pakage);


        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setPakageId(record.getPakage());
        applyInfo.setApplyState("已配送");
        applyInfoService.updateByPakageId(applyInfo);
        return recordService.save(record)?Result.suc():Result.fail();
    }


    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return recordService.removeById(id) ? Result.suc() : Result.fail();
    }
}
