package com.lunas.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.lunas.reggie.dto.DishDto;
import com.lunas.reggie.entity.Dish;

import java.io.IOException;


/**
 * @author jektong
 * @date 2022年05月10日 20:47
 */
public interface DishService extends IService<Dish> {

    public void saveWithFlaovr(DishDto dto) ;

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dto);




}
