package com.lunas.reggie.dto;


import com.lunas.reggie.entity.Dish;
import com.lunas.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    /** 菜品口味*/
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
