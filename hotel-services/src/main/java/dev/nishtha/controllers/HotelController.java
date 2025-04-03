package dev.nishtha.controllers;

import dev.nishtha.contracts.HotelService;
import dev.nishtha.dtos.HotelDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping("/all")
    public List<HotelDTO> getHotels() {
        return hotelService.fetchHotels();
    }

    @GetMapping("/{id}")
    public HotelDTO getHotelById(@PathVariable("id") @NotNull Long id) {
        return hotelService.fetchHotelById(id).orElse(null);
    }

    @PostMapping("/create")
    public HotelDTO createHotel(@RequestBody @Validated HotelDTO hotelDTO) {
        return hotelService.saveHotel(hotelDTO);
    }

    @PutMapping("/update")
    public HotelDTO updateHotel(@RequestBody @Validated HotelDTO hotelDTO) {
        return hotelService.updateHotel(hotelDTO);
    }

    @PatchMapping("/modify/{id}")
    public HotelDTO modifyHotel(@PathVariable("id") Long id,
                                @RequestBody HotelDTO hotelDTO) {
        return hotelService.modifyHotel(id, hotelDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable("id") @NotNull Long id) {
        hotelService.removeHotel(id);
    }

}
