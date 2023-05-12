package com.njit.orderManager.controller.user;

import com.njit.orderManager.common.CommonResult;
import com.njit.orderManager.common.StatusCode;
import com.njit.orderManager.domain.Type;
import com.njit.orderManager.service.TypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/user")
public class TypeController {

    @Resource
    private TypeService typeService;

    @GetMapping("/listTypes")
    public CommonResult<List<Type>> listTypes() {
        CommonResult<List<Type>> commonResult = new CommonResult<>();

        List<Type> list = typeService.list();

        commonResult.setData(list);
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }
}
