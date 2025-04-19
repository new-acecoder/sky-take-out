package com.sky.controller.user;

import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * C端菜品浏览
 */
@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        //构造redis的key
        String key = "dish_" + categoryId;
        //查询redis中是否有菜品缓存
        List<DishVO> list = (List<DishVO>) redisTemplate.opsForValue().get(key);
        if(list != null && list.size() > 0) {
            log.info("查询redis缓存中的菜品数据：{}", list);
            //如果有直接返回
            return Result.success(list);
        }

        //如果没有，查询数据库，将数据存入redis
        list = dishService.listWithFlavor(categoryId);
        redisTemplate.opsForValue().set(key,list);
        return Result.success(list);

    }

}
