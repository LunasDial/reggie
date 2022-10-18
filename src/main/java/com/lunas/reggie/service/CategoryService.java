package com.lunas.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lunas.reggie.entity.Category;


public interface CategoryService extends IService<Category> {
    public void remove(Long id);


}
