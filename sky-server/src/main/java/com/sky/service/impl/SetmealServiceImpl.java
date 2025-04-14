package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.apache.logging.log4j.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDTO setmealDTO) {
        //DTO给entity拷贝属性
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);

        //向套餐表插入数据
        setmealMapper.insert(setmeal);

        //获取jdbc生成的套餐id
        Long setmealId = setmeal.getId();

        //获取套餐中的菜品集合
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //给setmeal_dish表中套餐中的每个菜品赋套餐Id
        setmealDishes.stream().forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
        });
        //向setmeal_dish中批量保存套餐和菜品的关联关系
        setmealDishMapper.insertBatch(setmealDishes);

    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        //起售的套餐不能删除
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getByid(id);
            if(setmeal.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });
        /*
        ids.forEach(setmealId -> {
           //删除套餐表的数据
           setmealMapper.deleteById(setmealId);
           //删除套餐菜品关系表中到数据
            setmealDishMapper.deleteBySetmealId(setmealId);
        });*/
        //批量删除套餐表的数据
        setmealMapper.deleteBatchBySetmealIds(ids);
        //批量删除套餐菜品关系表中到数据
        setmealDishMapper.deleteBatchBySetealIds(ids);
    }
}
