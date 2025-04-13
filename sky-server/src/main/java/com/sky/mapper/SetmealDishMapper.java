package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品Id查对应的套餐Id
     * @param dishIds
     * @return
     */
    //@Select("select setmeal_id from setmeal_dish where dish_id in")
    List<Long> getSetmealIdsByDishId(List<Long> dishIds);

    /**
     * 批量保存套餐和菜品的关联关系
     *  @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
