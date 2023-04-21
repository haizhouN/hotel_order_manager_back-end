package com.rabbiter.hotel.controller.admin;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbiter.hotel.common.CommonResult;
import com.rabbiter.hotel.common.StatusCode;
import com.rabbiter.hotel.domain.Admin;
import com.rabbiter.hotel.dto.AdminLoginDTO;
import com.rabbiter.hotel.service.AdminService;
import com.rabbiter.hotel.util.WebUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController("adminAdminController")
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @PostMapping(value = "/login")
    public CommonResult<String> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        CommonResult<String> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("email", adminLoginDTO.getUserName());
        String username=adminLoginDTO.getUserName();
        String passward=adminLoginDTO.getPassword();
//        String md5Password = SecureUtil.md5(adminLoginDTO.getPassword());//e10adc3949ba59abbe56e057f20f883e
        queryWrapper.eq("password", adminLoginDTO.getPassword());
        Admin admin = adminService.getOne(queryWrapper);
        System.out.println(admin);

        if (null != admin) {
            WebUtils.getSession().setAttribute("loginAdmin", admin);

            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("登录成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("账号密码错误，请重试");
        }

        return commonResult;
    }

    @GetMapping("/logout")
    public CommonResult<String> logout(){
        CommonResult<String> commonResult = new CommonResult<>();

        WebUtils.getSession().removeAttribute("loginAdmin");

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData("登出成功!");

        return commonResult;
    }
}
