package dev.nishtha.core.controllers;

import dev.nishtha.core.dtos.HotelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class HotelWebController {

    private static final String SECURED_URL = "/secured/web/hotels";
    private static final String PUBLIC_URL = "/public/web/hotels";

    private final HotelController hotelController;

    @GetMapping(PUBLIC_URL + "/list")
    public String serveHotelsPage(Model model) {
        model.addAttribute("title", "List of Hotels - WEB");
        model.addAttribute("hotels", hotelController.getHotels());
        return "hotels/hotels";
    }

    @GetMapping(PUBLIC_URL + "/details/{id}")
    public String hotelDetails(Model model, @PathVariable String id) {
        model.addAttribute("title", "Hotel Details - WEB");
        model.addAttribute("hotel", hotelController.getHotelById(Long.parseLong(id)));
        return "hotels/hotel";
    }

    @GetMapping(PUBLIC_URL + "/add")
    public String serveRegisterHotelPage(Model model) {
        model.addAttribute("title", "Add Hotel - WEB");
        if ( !model.containsAttribute("hotelDto") ) {
            model.addAttribute("hotelDto", HotelDTO.builder().build());
        }
        return "hotels/register";
    }

    @PostMapping(SECURED_URL + "/create")
    public String createHotel(@ModelAttribute("hotelDto") @Validated HotelDTO hotelDto,
                              RedirectAttributes rAtt) {

        ResponseEntity<HotelDTO> response = hotelController.createHotel(hotelDto);
        HotelDTO savedHotel = response.getBody();

        if (savedHotel != null && savedHotel.getId() != null) {
            rAtt.addFlashAttribute("hotelId", savedHotel.getId());
            rAtt.addFlashAttribute("success", true);
        }

        return "redirect:/public/web/hotels/add";

    }

}
