package com.njit.orderManager.controller.user;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njit.orderManager.common.CommonResult;
import com.njit.orderManager.common.StatusCode;
import com.njit.orderManager.domain.User;
import com.njit.orderManager.dto.LoginDTO;
import com.njit.orderManager.dto.PasswordDTO;
import com.njit.orderManager.dto.RegisterDTO;
import com.njit.orderManager.dto.ReturnUserDTO;
import com.njit.orderManager.service.UserService;
import com.njit.orderManager.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/register")
    public CommonResult<String> register(@RequestBody RegisterDTO registerDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        // System.out.println(user);
        userService.save(user);
        commonResult.setData("注册成功");
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }

    @PostMapping(value = "/login")
    public CommonResult<String> login(@RequestBody LoginDTO loginDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("email", loginDTO.getEmail());
        queryWrapper.eq("password", loginDTO.getPassword());
        User user = userService.getBaseMapper().selectOne(queryWrapper);

        if (null != user) {
            WebUtils.getSession().setAttribute("loginUser", user);
//            System.out.println(WebUtils.getSession().getId());
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("登录成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("账号密码错误，请重试");
        }
        System.out.println(commonResult);
        return commonResult;
    }

    @GetMapping("/logout")
    public CommonResult<String> logout(){
        CommonResult<String> commonResult = new CommonResult<>();

        WebUtils.getSession().removeAttribute("loginUser");

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData("登出成功!");

        return commonResult;
    }

    @GetMapping("/userDetail")
    public CommonResult<ReturnUserDTO> userDetail() {
        CommonResult<ReturnUserDTO> commonResult = new CommonResult();
        ReturnUserDTO returnUser = new ReturnUserDTO();


        User user2 = (User) WebUtils.getSession().getAttribute("loginUser");
//        System.out.println(WebUtils.getSession().getId());
        User user = userService.getById(user2.getId());
//        System.out.println(user);
        BeanUtils.copyProperties(user, returnUser);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(returnUser);

        return commonResult;
    }

    @PostMapping("/updatePassword")
    public CommonResult<String> updatePassword(@RequestBody PasswordDTO passwordDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        System.out.println(passwordDTO);



        User user2 = (User) WebUtils.getSession().getAttribute("loginUser");
        User user = userService.getById(user2.getId());

        String md5OldPassword = SecureUtil.md5(passwordDTO.getOldPassword());

        if (!user.getPassword().equals(md5OldPassword)) {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("密码错误");

            return commonResult;
        }

        String md5NewPassword = SecureUtil.md5(passwordDTO.getNewPassword());
        user.setPassword(md5NewPassword);
        userService.updateById(user);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData("修改密码成功");

        return commonResult;
    }

}
