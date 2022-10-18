package com.lunas.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.lunas.reggie.entity.ShoppingCart;
import com.lunas.reggie.service.ShoppingCartService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {



}
