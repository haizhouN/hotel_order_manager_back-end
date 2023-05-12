package com.njit.orderManager.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.njit.orderManager.common.CommonResult;
import com.njit.orderManager.common.StatusCode;
import com.njit.orderManager.domain.Order;
import com.njit.orderManager.domain.Room;
import com.njit.orderManager.domain.Type;
import com.njit.orderManager.domain.User;
import com.njit.orderManager.dto.BookDTO;
import com.njit.orderManager.dto.DateSectionDTO;
import com.njit.orderManager.dto.ReturnRoomDTO;
import com.njit.orderManager.dto.TypeDTO;
import com.njit.orderManager.service.OrderService;
import com.njit.orderManager.service.RoomService;
import com.njit.orderManager.service.TypeService;
import com.njit.orderManager.service.UserService;
import com.njit.orderManager.util.WebUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
public class RoomController {

    @Resource
    private RoomService roomService;
    @Resource
    private OrderService orderService;
    @Resource
    private TypeService typeService;
    @Resource
    private UserService userService;

    @PostMapping(value = "/listRoom")
    public CommonResult<List<ReturnRoomDTO>> listRoom(@RequestBody DateSectionDTO dateSectionDTO) {
        CommonResult<List<ReturnRoomDTO>> commonResult = new CommonResult<>();

        List<ReturnRoomDTO> list = roomService.listRooms(dateSectionDTO);

        commonResult.setData(list);
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }

    @PostMapping(value = "/roomDetail")
    public CommonResult<ReturnRoomDTO> roomDetail(@RequestParam("roomId") Integer roomId) {
        CommonResult<ReturnRoomDTO> commonResult = new CommonResult<>();

        ReturnRoomDTO returnRoomDTO = roomService.roomDetail(roomId);
//        System.out.println(returnRoomDTO);
        commonResult.setData(returnRoomDTO);
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }

    @PostMapping("/bookRoom")
    public CommonResult<String> bookRoom(@RequestBody BookDTO bookDTO) {
        CommonResult<String> commonResult = new CommonResult<>();

        // User user1 = new User();
        // user1.setId(6);
        // WebUtils.getSession().setAttribute("loginUser", user1);

        User user = (User) WebUtils.getSession().getAttribute("loginUser");

        Room room = roomService.getById(bookDTO.getRoomId());
        Type type = typeService.getById(room.getType());
        Order order = new Order();
        BeanUtils.copyProperties(bookDTO,  order);
        order.setUserId(user.getId());

        int days = (int) Math.ceil((bookDTO.getLeaveTime().getTime() - bookDTO.getInTime().getTime()) / (60 * 60 * 24 * 1000 * 1.0));
        // System.out.println(days);

        order.setRealPrice(type.getPrice() * days);
        // System.out.println(order);

        orderService.save(order);

        user.setState(1);
        userService.updateById(user);

        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        commonResult.setData("预订成功!");

        return commonResult;
    }

    @PostMapping("/listRoomsByTypeId")
    public CommonResult<List<ReturnRoomDTO>> listRoomsByTypeId(@RequestBody TypeDTO typeDTO) {
        CommonResult<List<ReturnRoomDTO>> commonResult = new CommonResult<>();
        QueryWrapper queryWrapper = new QueryWrapper();

        DateSectionDTO dateSectionDTO = new DateSectionDTO();
        BeanUtils.copyProperties(typeDTO, dateSectionDTO);
        List<ReturnRoomDTO> roomList = roomService.listRooms(dateSectionDTO);

        List<ReturnRoomDTO> returnRoomList = new ArrayList<>();
        if (0 != roomList.size()) {
            for (ReturnRoomDTO room : roomList) {
                if (typeDTO.getTypeId().equals(room.getType().getId())) {
                    returnRoomList.add(room);
                }
            }
        }

        commonResult.setData(returnRoomList);
        commonResult.setCode(StatusCode.COMMON_SUCCESS.getCode());
        commonResult.setMessage(StatusCode.COMMON_SUCCESS.getMessage());
        return commonResult;
    }

}
