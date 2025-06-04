package dev.nishtha.core.controllers;

import dev.nishtha.core.contracts.HotelService;
import dev.nishtha.core.dtos.HotelDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class HotelController {

    private final HotelService hotelService;
    private static final String PUBLIC_URL = "/public/api/hotels";
    private static final String SECURED_URL = "/secured/api/hotels";

    @GetMapping(PUBLIC_URL + "/all")
    public List<HotelDTO> getHotels() {
        return hotelService.fetchHotels();
    }

    @GetMapping(PUBLIC_URL + "/details/{id}")
    public HotelDTO getHotelById(@PathVariable("id") @NotNull Long id) {
        return hotelService.fetchHotelById(id).orElse(null);
    }

    @PostMapping(SECURED_URL + "/create")
    public ResponseEntity<HotelDTO> createHotel(@RequestBody @Validated HotelDTO hotelDTO) {
        HotelDTO savedHotel = hotelService.saveHotel(hotelDTO);
        return ResponseEntity.created(URI.create(PUBLIC_URL + "/details/" + savedHotel.getId()))
                .body(savedHotel);
    }

    @PutMapping(SECURED_URL + "/update")
    public HotelDTO updateHotel(@RequestBody @Validated HotelDTO hotelDTO) {
        return hotelService.updateHotel(hotelDTO);
    }

    @PatchMapping(SECURED_URL + "/modify/{id}")
    public HotelDTO modifyHotel(@PathVariable("id") Long id,
                                @RequestBody HotelDTO hotelDTO) {
        return hotelService.modifyHotel(id, hotelDTO);
    }

    @DeleteMapping(SECURED_URL + "/delete/{id}")
    public void deleteHotel(@PathVariable("id") @NotNull Long id) {
        hotelService.removeHotel(id);
    }

}
