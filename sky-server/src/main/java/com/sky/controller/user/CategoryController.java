package com.sky.controller.user;

import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * C端分类接口
 */
@RestController("userCategoryController")
@RequestMapping("/user/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据分类查询菜品
     * @param type
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        log.info("根据分类查询菜品：{}", type);
        //调用分类服务
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
