package com.lunas.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunas.reggie.dto.DishDto;
import com.lunas.reggie.entity.Dish;
import com.lunas.reggie.entity.DishFlavor;
import com.lunas.reggie.mapper.DishMapper;
import com.lunas.reggie.service.DishFlavorService;
import com.lunas.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jektong
 * @date 2022年05月10日 20:15
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private  DishFlavorService dishFlavorService;


    @Override
    @Transactional
    public void saveWithFlaovr(DishDto dto) {
        //因为DishDto是包含了Dish的信息，所以可以先存Dish信息到Dish表中，DishDto扩展的数据可以下一步再存
        //为什么这里传dishDto可以，因为DishDto是Dish的子类
        this.save(dto);
        //拿ID和口味List，为存DishDto做准备
        Long dishId = dto.getId();
        List<DishFlavor> flavor = dto.getFlavors();
        //遍历
        flavor.stream().map((item)->{
            item.setDishId(dishId);
            return item;

        }).collect(Collectors.toList());
        //saveBatch是批量集合的存储
        dishFlavorService.saveBatch(flavor);

    }

    /**
     * 根据ID查询菜品信息以及对应的口味信息
     * @param id
     * @return
     */

    public DishDto getByIdWithFlavor(Long id) {

        Dish dish=this.getById(id);

        DishDto dto = new DishDto();
        BeanUtils.copyProperties(dish,dto);

        LambdaQueryWrapper<DishFlavor> dishFlavorQueryWrapper= new LambdaQueryWrapper<>();
        dishFlavorQueryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorQueryWrapper);
        dto.setFlavors(dishFlavorList);
        return dto;
    }


    @Override
    public void updateWithFlavor(DishDto dto) {
        this.updateById(dto);
        LambdaQueryWrapper<DishFlavor> dishFlavorQueryWrapper= new LambdaQueryWrapper<>();
        dishFlavorQueryWrapper.eq(DishFlavor::getDishId,dto.getId());

        dishFlavorService.remove(dishFlavorQueryWrapper);

        List<DishFlavor> flavor = dto.getFlavors();

        //遍历
        flavor.stream().map((item)->{
            item.setDishId(dto.getId());
            return item;

        }).collect(Collectors.toList());


    }
}
