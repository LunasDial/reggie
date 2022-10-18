package com.lunas.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lunas.reggie.commom.R;
import com.lunas.reggie.entity.Category;
import com.lunas.reggie.entity.Employee;
import com.lunas.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> pageInfo = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Category> query = new LambdaQueryWrapper<>();
        //添加排序条件
        query.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, query);

        return R.success(pageInfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类，id为：{}",ids);
         //categoryService.removeById(ids);
        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }


    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息{}" + category);
        categoryService.updateById(category);
        return R.success("分类修改成功");
    }


    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        // 条件构造
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        // 排序
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);

    }




}
