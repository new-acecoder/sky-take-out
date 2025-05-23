package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
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
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 批量保存套餐和菜品的关联关系
     *  @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);


    /**
     * 根据套餐id删除套餐和菜品的关联关系
     * @param setmealId
     */
    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据套餐ids批量删除套餐菜品关系表中到数据
     * @param setmealIds
     */
    void deleteBatchBySetealIds(List<Long> setmealIds);

    /**
     * 根据套餐id获取关联菜品集合
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);
}
