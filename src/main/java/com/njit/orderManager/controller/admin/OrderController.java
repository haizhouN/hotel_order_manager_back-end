package com.njit.orderManager.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njit.orderManager.common.CommonResult;
import com.njit.orderManager.common.StatusCode;
import com.njit.orderManager.domain.Order;
import com.njit.orderManager.domain.User;
import com.njit.orderManager.service.OrderService;
import com.njit.orderManager.service.RoomService;
import com.njit.orderManager.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController("adminOrderController")
@RequestMapping("/admin")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private RoomService roomService;
    @Resource
    private UserService userService;

    @GetMapping("/allListOrders")
    public CommonResult<List<Order>> allListOrders() {
        CommonResult<List<Order>> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();


        List<Order> userList = orderService.list(queryWrapper);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(userList);

        return commonResult;
    }
    @GetMapping("/listOrders")
    public CommonResult<List<Order>> listOrders() {
        CommonResult<List<Order>> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();

        queryWrapper.eq("flag", 0);
//        queryWrapper.eq("id",2);
        List<Order> userList = orderService.list(queryWrapper);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(userList);

        return commonResult;
    }

    @PostMapping("/unsubscribe")
    public CommonResult<String> unsubscribe(@RequestParam("orderId") Integer orderId) {
        CommonResult<String> commonResult = new CommonResult<>();

        Order order = orderService.getById(orderId);
        order.setFlag(2);
        boolean result = orderService.updateById(order);

        if (result) {
            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("退订成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("退订失败");
        }

        return commonResult;
    }

    @PostMapping("/handle")
    public CommonResult<String> handle(@RequestParam("orderId") Integer orderId) {
        CommonResult<String> commonResult = new CommonResult<>();

        Order order = orderService.getById(orderId);
        order.setFlag(1);
        boolean result = orderService.updateById(order);

        if (result) {
            roomService.bookRoom(order.getRoomId());
            User user = userService.getById(order.getUserId());
            int jifen = (int) (user.getJifen() + order.getRealPrice());
            user.setJifen(jifen);
            userService.updateById(user);

            commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
            commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
            commonResult.setData("办理入住成功");
        } else {
            commonResult.setCode(StatusCode.COMMON_FAIL.getCode());
            commonResult.setMessage(StatusCode.COMMON_FAIL.getMessage());
            commonResult.setData("办理入住失败");
        }

        return commonResult;
    }
}
