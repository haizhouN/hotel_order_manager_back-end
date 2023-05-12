package com.njit.orderManager.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njit.orderManager.common.CommonResult;
import com.njit.orderManager.common.StatusCode;
import com.njit.orderManager.domain.Order;
import com.njit.orderManager.domain.User;
import com.njit.orderManager.dto.ReturnOrderDTO;
import com.njit.orderManager.dto.ReturnRoomDTO;
import com.njit.orderManager.dto.ReturnUserDTO;
import com.njit.orderManager.service.OrderService;
import com.njit.orderManager.service.RoomService;
import com.njit.orderManager.service.UserService;
import com.njit.orderManager.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private RoomService roomService;
    @Resource
    private UserService userService;

    @GetMapping("/historyOrder")
    public CommonResult<List<ReturnOrderDTO>> historyOrder() {
        CommonResult<List<ReturnOrderDTO>> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();

        // User user1 = new User();
        // user1.setId(6);
        // WebUtils.getSession().setAttribute("loginUser", user1);

        User user = (User) WebUtils.getSession().getAttribute("loginUser");

        queryWrapper.eq("user_id", user.getId());
        List<Order> list = orderService.list(queryWrapper);

        List<ReturnOrderDTO> orderDTOList = new ArrayList<>();
        if (0 != list.size()) {
            for (Order order : list) {
                ReturnOrderDTO orderDTO = new ReturnOrderDTO();
                ReturnRoomDTO roomDTO = roomService.roomDetail(order.getRoomId());

                orderDTO.setOrder(order);
                orderDTO.setRoom(roomDTO);

                orderDTOList.add(orderDTO);
            }
        }

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(orderDTOList);

        return commonResult;
    }

    @PostMapping("/detailOrder")
    public CommonResult<ReturnOrderDTO> detailOrder(@RequestParam("orderId") Integer orderId) {
        CommonResult<ReturnOrderDTO> commonResult = new CommonResult<>();
        ReturnOrderDTO returnOrder = new ReturnOrderDTO();
        ReturnUserDTO userDTO = new ReturnUserDTO();

        Order order = orderService.getById(orderId);
        User user = userService.getById(order.getUserId());
        BeanUtils.copyProperties(user, userDTO);
        ReturnRoomDTO returnRoomDTO = roomService.roomDetail(order.getRoomId());

        returnOrder.setOrder(order);
        returnOrder.setUser(userDTO);
        returnOrder.setRoom(returnRoomDTO);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData(returnOrder);

        return commonResult;
    }

}
