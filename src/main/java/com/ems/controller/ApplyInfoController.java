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
import com.ems.service.ApplyInfoService;
import com.ems.service.PakageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

/**
 * @author ZJ
 * @data 2023/3/20 15:11
 */
@RestController
@RequestMapping("/applyInfo")
public class ApplyInfoController {

    @Autowired
    private ApplyInfoService applyInfoService;

    //增
    @PostMapping("/save")
    public Result save(@RequestBody ApplyInfo applyInfo) {
        applyInfo.setApplyTime(new Date());
        return applyInfoService.save(applyInfo) ? Result.suc() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody ApplyInfo applyInfo) {
        return applyInfoService.updateById(applyInfo) ? Result.suc() : Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return applyInfoService.removeById(id) ? Result.suc() : Result.fail();
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

        Page<ApplyInfo> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<ApplyInfo> lambdaQueryWrapper = new LambdaQueryWrapper();
//        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
//            lambdaQueryWrapper.like(ApplyInfo::getName, name);
//        }
//        if (StringUtils.isNotBlank(pakagetype) && !"null".equals(pakagetype)) {
//            lambdaQueryWrapper.eq(ApplyInfo::getPakagetype, pakagetype);
//        }
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.eq(ApplyInfo::getWrapNo, name);
        }
        if (!ObjectUtils.isNull(biao)) {
            lambdaQueryWrapper.eq(ApplyInfo::getApplyUserId, biao);
        }

        IPage result = applyInfoService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }
}
