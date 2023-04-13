package com.ems.controller;


import com.ems.common.Result;
import com.ems.entity.Menu;
import com.ems.entity.User;
import com.ems.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ems
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List list=menuService.lambdaQuery().like(Menu::getMenuright,roleId).list();
        return Result.suc(list);
    }

}
