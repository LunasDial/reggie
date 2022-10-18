package com.lunas.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lunas.reggie.entity.OrderDetail;
import com.lunas.reggie.mapper.OrderDetailMapper;
import com.lunas.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}