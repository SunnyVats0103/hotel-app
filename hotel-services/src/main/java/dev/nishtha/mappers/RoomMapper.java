package dev.nishtha.mappers;

import dev.nishtha.dtos.HotelDTO;
import dev.nishtha.dtos.RoomDTO;
import dev.nishtha.entities.Hotel;
import dev.nishtha.entities.Room;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class RoomMapper {

    public static RoomDTO toRoomDTO(Room room) {
        DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String formattedDate = dateFormatter.print(room.getCreatedAt(), Locale.getDefault());
        String hotelId = room.getHotel() != null ? String.valueOf(room.getHotel().getId()) : null;

        return RoomDTO.builder()
                .id(room.getId())
                .createdAt(formattedDate)
                .hotelId(hotelId)
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .capacity(room.getCapacity())
                .description(room.getDescription())
                .price(room.getPrice())
                .isBooked(room.getIsBooked())
                .build();
    }

    public static Room toRoom(RoomDTO roomDTO) {
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
