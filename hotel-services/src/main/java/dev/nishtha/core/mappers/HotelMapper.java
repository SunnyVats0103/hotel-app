package dev.nishtha.core.mappers;

import dev.nishtha.core.dtos.HotelDTO;
import dev.nishtha.core.dtos.RoomDTO;
import dev.nishtha.core.dtos.UserRegisterDTO;
import dev.nishtha.core.entities.Hotel;
import dev.nishtha.core.utils.SimpleDateFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Component
public class HotelMapper {

    private final RoomMapper roomMapper;
    private final SimpleDateFormatUtil formatter;

    public HotelDTO mapHotel(Hotel hotel) {
        UserRegisterDTO owner = hotel.getUserEntity() != null && hotel.getUserEntity().getId() != 0L ? UserRegisterDTO.builder()
                .name(hotel.getUserEntity().getName())
                .email(hotel.getUserEntity().getEmail())
                .phone(hotel.getUserEntity().getPhone())
                .build() : null;
        List<RoomDTO> rooms = null;
        if (hotel.getRooms() != null) {
            rooms = new ArrayList<>(hotel.getRooms().stream().map(roomMapper::toRoomDTO).toList());
        }

        return HotelDTO.builder()
                .id(hotel.getId())
                .registeredOn(formatter.formatToUTC(hotel.getCreatedAt()))
                .name(hotel.getName())
                .email(hotel.getEmail())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .state(hotel.getState())
                .country(hotel.getCountry())
                .phone(hotel.getPhone())
                .zipCode(hotel.getZipCode())
                .isActive(!hotel.getIsDeleted())
                .rooms(rooms)
                .user(owner)
                .build();
    }

    public Hotel mapHotelDTO(HotelDTO hotelDTO) {
        return Hotel.builder()
                .name(hotelDTO.getName())
                .email(hotelDTO.getEmail())
                .address(hotelDTO.getAddress())
                .city(hotelDTO.getCity())
                .state(hotelDTO.getState())
                .country(hotelDTO.getCountry())
                .zipCode(hotelDTO.getZipCode())
                .phone(hotelDTO.getPhone())
                .build();
    }

}
