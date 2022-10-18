package com.lunas.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.lunas.reggie.dto.SetmealDto;
import com.lunas.reggie.entity.Setmeal;

import java.util.List;

/**
 * @author jektong
 * @date 2022年05月10日 19:44
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐，同时需要删除套餐和菜品的关联数据
     * @param ids
     */
    public void removeWithDish(List<Long> ids);

}


