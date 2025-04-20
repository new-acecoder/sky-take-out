package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * C端地址簿接口
 */
@RestController
@RequestMapping("/user/addressBook")
@Slf4j
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    @GetMapping("/list")
    public Result<List<AddressBook>> list(){
        // 获取当前登录用户的id
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = AddressBook.builder().userId(userId).build();
        List<AddressBook> list = addressBookService.list(addressBook);
        return Result.success(list);

    }
    /**
     * 新增地址
     */
    @PostMapping
    public Result save(@RequestBody AddressBook addressBook){
        addressBookService.save(addressBook);
        return Result.success();
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<AddressBook> getById(@PathVariable Long id){
        AddressBook addressBook = addressBookService.getById(id);
        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    public Result update(@RequestBody AddressBook addressBook){
        addressBookService.update(addressBook);
        return Result.success();
    }

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook){
        addressBookService.setDefault(addressBook);
        return Result.success();
    }

    /**
     * 根据id查询默认地址
     * @return
     */
    @GetMapping("/default")
    public Result<AddressBook> getDefault(){
        // 获取当前登录用户的id
        Long userId = BaseContext.getCurrentId();
        AddressBook addressBook = AddressBook.builder().userId(userId).isDefault(1).build();
        List<AddressBook> list = addressBookService.list(addressBook);
        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }
        return Result.error("没有默认地址");
    }

    /**
     * 根据id删除地址
     */
    @DeleteMapping
    public Result deleteById(Long id){
        addressBookService.deleteById(id);
        return Result.success();
    }

}
