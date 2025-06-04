package dev.nishtha.core.controllers;

import dev.nishtha.core.contracts.RoomService;
import dev.nishtha.core.dtos.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RoomController {

    private final RoomService roomService;
    private static final String PUBLIC_URL = "/public/api/rooms";
    private static final String SECURED_URL = "/secured/api/rooms";

    @GetMapping(PUBLIC_URL + "/all")
    public List<RoomDTO> getRooms() {
        return roomService.fetchRooms();
    }

    @GetMapping(PUBLIC_URL + "/{id}")
    public RoomDTO getRoomDetails(@PathVariable("id") Long id) {
        return roomService.fetchRoomById(id);
    }

    @PostMapping(SECURED_URL + "/create")
    public RoomDTO createRoom(@RequestBody @Validated RoomDTO roomDTO) {
        return roomService.saveRoom(roomDTO);
    }

    @PutMapping(SECURED_URL + "/update/{id}")
    public RoomDTO updateRoom(@PathVariable("id") Long id,
                              @RequestBody @Validated RoomDTO roomDTO) {
        return roomService.updateRoom(id, roomDTO);
    }

    @PatchMapping(SECURED_URL + "/modify/{id}")
    public RoomDTO modifyRoom(@PathVariable("id") Long id,
                              @RequestBody @Validated RoomDTO roomDTO) {
        return roomService.modifyRoom(id, roomDTO);
    }

    @DeleteMapping(SECURED_URL + "/{id}")
    public void deleteRoom(@PathVariable("id") Long id) {
        roomService.removeRoom(id);
    }

}
