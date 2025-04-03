package dev.nishtha.contracts;

import dev.nishtha.dtos.HotelDTO;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Optional;

public interface HotelService {

    List<HotelDTO> fetchHotels();
    Optional<HotelDTO> fetchHotelById(@NotNull Long id);
    HotelDTO saveHotel(HotelDTO hotelDTO);
    HotelDTO updateHotel(HotelDTO hotelDTO);
    HotelDTO modifyHotel(Long id, HotelDTO hotelDTO);
    void removeHotel(@NotNull Long id);

}
