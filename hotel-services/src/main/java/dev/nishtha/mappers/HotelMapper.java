package dev.nishtha.mappers;

import dev.nishtha.dtos.HotelDTO;
import dev.nishtha.dtos.OwnerDTO;
import dev.nishtha.dtos.RoomDTO;
import dev.nishtha.entities.Hotel;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class HotelMapper {

    public HotelDTO mapHotel(Hotel hotel) {
        OwnerDTO owner = hotel.getOwner() != null && hotel.getOwner().getId() != 0L ? OwnerDTO.builder()
                .name(hotel.getOwner().getName())
                .email(hotel.getOwner().getEmail())
                .phone(hotel.getOwner().getPhone())
                .build() : null;
        List<RoomDTO> rooms = null;
        if (hotel.getRooms() != null) {
            rooms = new ArrayList<>(hotel.getRooms().stream().map(RoomMapper::toRoomDTO).toList());
        }
        DateFormatter dateFormatter = new DateFormatter("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String formattedDate = dateFormatter.print(hotel.getCreatedAt(), Locale.getDefault());

        return HotelDTO.builder()
                .id(hotel.getId())
                .registeredOn(formattedDate)
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
                .owner(owner)
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
