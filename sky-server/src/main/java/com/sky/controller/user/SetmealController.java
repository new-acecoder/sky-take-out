package com.sky.controller.user;


import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * C端套餐浏览
 */
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据分类id查询套餐
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Setmeal>> list(Long categoryId) {
       /* Setmeal setmeal = new Setmeal();
        setmeal.setCategoryId(categoryId);
        //查询起售状态的套餐
        setmeal.setStatus(StatusConstant.ENABLE);*/
        List<Setmeal> list = setmealService.list(categoryId);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询套餐和关联的菜品数据
     * @param id
     * @return
     */
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> dishList(@PathVariable Long id){
        List<DishItemVO> list = setmealService.getDishItemById(id);
        return Result.success(list);
    }
}
