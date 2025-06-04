package dev.nishtha.core.contracts;

import dev.nishtha.core.dtos.RoomDTO;

import java.util.List;

public interface RoomService {

    List<RoomDTO> fetchRooms();
    RoomDTO fetchRoomById(Long id);
    RoomDTO saveRoom(RoomDTO roomDTO);
    RoomDTO updateRoom(Long id, RoomDTO roomDTO);
    RoomDTO modifyRoom(Long id, RoomDTO roomDTO);
    void removeRoom(Long id);

}
