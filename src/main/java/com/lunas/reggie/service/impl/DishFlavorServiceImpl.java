package com.lunas.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunas.reggie.entity.DishFlavor;
import com.lunas.reggie.mapper.DishFlavorMapper;
import com.lunas.reggie.service.DishFlavorService;
import com.lunas.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

}
