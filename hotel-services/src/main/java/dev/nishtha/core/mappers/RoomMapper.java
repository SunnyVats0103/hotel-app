package dev.nishtha.core.mappers;

import dev.nishtha.core.dtos.RoomDTO;
import dev.nishtha.core.entities.Room;
import dev.nishtha.core.utils.SimpleDateFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@RequiredArgsConstructor
@Component
public class RoomMapper {

    private final SimpleDateFormatUtil formatter;

    public RoomDTO toRoomDTO(Room room) {
        String hotelId = room.getHotel() != null ? String.valueOf(room.getHotel().getId()) : null;

        return RoomDTO.builder()
                .id(room.getId())
                .createdAt(formatter.formatToUTC(room.getCreatedAt()))
                .hotelId(hotelId)
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .capacity(room.getCapacity())
                .description(room.getDescription())
                .price(room.getPrice())
                .isBooked(room.getIsBooked())
                .build();
    }

    public Room toRoom(RoomDTO roomDTO) {
        return Room.builder()
                .roomType(roomDTO.getRoomType())
                .roomNumber(roomDTO.getRoomNumber())
                .capacity(roomDTO.getCapacity())
                .description(roomDTO.getDescription())
                .price(roomDTO.getPrice())
                .isBooked(roomDTO.getIsBooked())
                .build();
    }
}
