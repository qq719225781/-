package com.ems.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ems.common.QueryPageParam;
import com.ems.common.Result;
import com.ems.entity.Delivery;
import com.ems.entity.Pakage;
import com.ems.service.DeliveryService;
import com.ems.service.PakageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author ZJ
 * @data 2023/3/20 21:47
 */
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private PakageService pakageService;

    //增
    @PostMapping("/save")
    public Result save(@RequestBody Delivery delivery) {

        Pakage pakage = pakageService.getById(delivery.getWrapId());
        pakage.setIsDelete("1");
        pakageService.updateById(pakage);
        return deliveryService.save(delivery) ? Result.suc() : Result.fail();
    }

    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Delivery delivery) {
        return deliveryService.updateById(delivery) ? Result.suc() : Result.fail();
    }

    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id) {
        return deliveryService.removeById(id) ? Result.suc() : Result.fail();
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

        Page<Delivery> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Delivery> lambdaQueryWrapper = new LambdaQueryWrapper();
//        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
//            lambdaQueryWrapper.like(ApplyInfo::getName, name);
//        }
//        if (StringUtils.isNotBlank(pakagetype) && !"null".equals(pakagetype)) {
//            lambdaQueryWrapper.eq(ApplyInfo::getPakagetype, pakagetype);
//        }delivery_user_name
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.eq(Delivery::getDeliveryUserName, name);
        }
        if (!ObjectUtils.isNull(biao)) {
            lambdaQueryWrapper.eq(Delivery::getDeliveryUserId, biao);
        }

        IPage result = deliveryService.pageCC(page, lambdaQueryWrapper);

        return Result.suc(result.getRecords(), result.getTotal());
    }

}
