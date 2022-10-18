package com.lunas.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lunas.reggie.entity.AddressBook;
import com.lunas.reggie.mapper.AddressBookMapper;
import com.lunas.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook > implements AddressBookService {
}
