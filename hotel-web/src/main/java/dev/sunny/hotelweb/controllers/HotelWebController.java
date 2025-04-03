package dev.sunny.hotelweb.controllers;

import dev.nishtha.dtos.HotelDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@RequiredArgsConstructor
@Controller
@RequestMapping("/web/hotels")
public class HotelWebController {

    private final WebClient webClient;

    @GetMapping("/list")
    public String hotels(Model model) {
        String URL = "/api/hotels/all";
        model.addAttribute("title", "List of Hotels");

        HotelDTO[] response;
        try {
            response = webClient.method(HttpMethod.GET)
                    .uri(URL)
                    .retrieve()
                    .bodyToMono(HotelDTO[].class)
                    .block();
        } catch (ResourceAccessException rae) {
            model.addAttribute("error", "Unable to connect to the server. Please try again later.");
            model.addAttribute("title", "Error Page");
            return "error";
        }

        if (response != null)
            model.addAttribute("hotels", Arrays.stream(response).toList());

        return "hotels/hotels";
    }

    @GetMapping("/details/{id}")
    public String hotelDetails(Model model, @PathVariable String id) {
        String URL = "/api/hotels/" + id;
        model.addAttribute("title", "Hotel Details");

        HotelDTO response = findHotelByID(model, URL);
        if (response == null) return "error";

        model.addAttribute("hotel", response);
        return "hotels/hotel";
    }

    @GetMapping("/create")
    public String registerHotelForm(Model model) {
        model.addAttribute("title", "Create Hotel");

        if (model.containsAttribute("isResponse")) {
            if (model.containsAttribute("success")) {
                HotelDTO savedDto = (HotelDTO) model.getAttribute("savedHotel");
                if (savedDto != null) {
                    model.addAttribute("url", "/web/hotels/details/" + savedDto.getId());
                    model.addAttribute("success", true);
                }
            }

            if (model.containsAttribute("error")) {
                model.addAttribute("error", model.getAttribute("error"));
            }
        }
        model.addAttribute("hotelDto", HotelDTO.builder().build());

        return "hotels/register";
    }

    @PostMapping("/register")
    public String registerHotel(@ModelAttribute("hotel") HotelDTO hotel,
                                RedirectAttributes rAtt) {
        String URL = "/api/hotels/create";
        HotelDTO response;
        try {
            response = webClient.post()
                    .uri(URL)
                    .body(Mono.just(hotel), HotelDTO.class)
                    .retrieve()
                    .bodyToMono(HotelDTO.class)
                    .block();
        } catch (ResourceAccessException rae) {
            rAtt.addFlashAttribute("error", "Unable to connect to the server. Please try again later.");
            return "redirect:/web/hotels/create";
        } catch (HttpClientErrorException | HttpServerErrorException httpException) {
            rAtt.addFlashAttribute("error", httpException.getMessage());
            return "redirect:/web/hotels/create";
        } catch (IllegalArgumentException e) {
            rAtt.addFlashAttribute("error", "Invalid hotel data. Please check the input.");
            return "redirect:/web/hotels/create";
        }

        if (response != null) {
            rAtt.addFlashAttribute("savedHotel", response);
            rAtt.addFlashAttribute("success", true);
        }
        rAtt.addFlashAttribute("isResponse", true);
        return "redirect:/web/hotels/create";
    }

    @GetMapping("/update/{id}")
    public String updateHotelForm(Model model, @PathVariable String id) {
        String URL = "/api/hotels/" + id;
        model.addAttribute("title", "Update Hotel");

        HotelDTO response = findHotelByID(model, URL);
        if (response == null) return "error";

        model.addAttribute("hotel", response);

        return "hotels/updateForm";
    }

    private HotelDTO findHotelByID(Model model, String URL) {
        HotelDTO response;
        try {
            response = webClient.method(HttpMethod.GET)
                    .uri(URL)
                    .retrieve()
                    .bodyToMono(HotelDTO.class)
                    .block();
        } catch (ResourceAccessException rae) {
            model.addAttribute("error", "Unable to connect to the server. Please try again later.");
            model.addAttribute("title", "Error Page");
            return null;
        }
        return response;
    }

    @PostMapping("/update/{id}")
    public String updateHotel(@ModelAttribute("hotel") HotelDTO hotel,
                              @PathVariable("id") String id,
                               RedirectAttributes rAtt) {
        String URL = "/api/hotels/modify/" + id;
        HotelDTO response;
        try {
            response = webClient.method(HttpMethod.PATCH)
                    .uri(URL)
                    .body(Mono.just(hotel), HotelDTO.class)
                    .retrieve()
                    .bodyToMono(HotelDTO.class)
                    .block();
        } catch (ResourceAccessException rae) {
            rAtt.addFlashAttribute("error", "Unable to connect to the server. Please try again later.");
            return "redirect:/web/hotels/details/" + hotel.getId();
        } catch (HttpClientErrorException | HttpServerErrorException httpException) {
            rAtt.addFlashAttribute("error", httpException.getMessage());
            return "redirect:/web/hotels/details/" + hotel.getId();
        } catch (IllegalArgumentException e) {
            rAtt.addFlashAttribute("error", "Invalid hotel data. Please check the input.");
            return "redirect:/web/hotels/details/" + hotel.getId();
        }

        if (response != null) {
            rAtt.addFlashAttribute("savedHotel", response);
            rAtt.addFlashAttribute("success", true);
        }
        rAtt.addFlashAttribute("isResponse", true);
        return "redirect:/web/hotels/details/" + hotel.getId();
    }

}
