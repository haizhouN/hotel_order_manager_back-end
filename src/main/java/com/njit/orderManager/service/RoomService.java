package com.njit.orderManager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njit.orderManager.domain.Room;
import com.njit.orderManager.dto.AdminReturnRoomDTO;
import com.njit.orderManager.dto.DateSectionDTO;
import com.njit.orderManager.dto.ReturnRoomDTO;

import java.util.List;


public interface RoomService extends IService<Room> {

    Boolean bookRoom(Integer roomId);

    Boolean finishRoom(Integer roomId);

    List<ReturnRoomDTO> listRooms(DateSectionDTO dateSectionDTO);

    ReturnRoomDTO roomDetail(Integer roomId);

    AdminReturnRoomDTO adminRoomDetail(Integer roomId);
}
