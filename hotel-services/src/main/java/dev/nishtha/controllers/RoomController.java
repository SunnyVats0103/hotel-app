package dev.nishtha.controllers;

import dev.nishtha.contracts.RoomService;
import dev.nishtha.dtos.RoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/all")
    public List<RoomDTO> getRooms() {
        return roomService.fetchRooms();
    }

    @GetMapping("/{id}")
    public RoomDTO getRoomById(@PathVariable("id") Long id) {
        return roomService.fetchRoomById(id);
    }

    @PostMapping("/create")
    public RoomDTO createRoom(@RequestBody @Validated RoomDTO roomDTO) {
        return roomService.saveRoom(roomDTO);
    }

    @PutMapping("/update/{id}")
    public RoomDTO updateRoom(@PathVariable("id") Long id,
                              @RequestBody @Validated RoomDTO roomDTO) {
        return roomService.updateRoom(id, roomDTO);
    }

    @PatchMapping("/modify/{id}")
    public RoomDTO modifyRoom(@PathVariable("id") Long id,
                              @RequestBody @Validated RoomDTO roomDTO) {
        return roomService.modifyRoom(id, roomDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable("id") Long id) {
        roomService.removeRoom(id);
    }

}
