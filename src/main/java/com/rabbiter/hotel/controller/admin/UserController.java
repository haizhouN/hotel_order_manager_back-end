package com.rabbiter.hotel.controller.admin;

import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.User;
import com.rabbiter.hotel.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController("adminUserController")
@RequestMapping("/admin")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/listUsers")
    public CommonResult<List<User>> listUsers() {
        CommonResult<List<User>> commonResult = new CommonResult<>();

        List<User> userList = userService.list();

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(userList);

        return commonResult;
    }

    @PostMapping("/deleteUser")
    public CommonResult<String> deleteUser(@RequestParam("userId") Integer userId) {
        CommonResult<String> commonResult = new CommonResult<>();

        boolean result = userService.removeById(userId);

        if (result) {
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("删除成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("删除失败");
        }

        return commonResult;
    }

    @PostMapping("/updateUser")
    public CommonResult<String> updateUser(@RequestBody User user) {
        CommonResult<String> commonResult = new CommonResult<>();

        boolean result = userService.updateById(user);

        if (result) {
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("修改成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("修改失败");
        }

        return commonResult;
    }

    @PostMapping("/getUserById")
    public CommonResult<User> getUserById(@RequestParam("userId") Integer userId) {
        CommonResult<User> commonResult = new CommonResult<>();

        User user = userService.getById(userId);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(user);

        return commonResult;
    }

}
