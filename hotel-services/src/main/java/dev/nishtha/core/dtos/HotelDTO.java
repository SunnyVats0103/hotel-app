package dev.nishtha.core.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDTO {

    private UserRegisterDTO user;

    private Long id;

    private String registeredOn;

    @NotNull
    @NotBlank
    private String name;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @Length(min = 5, message = "must be at least 5 characters.")
    private String address;

    private String city;

    @NotNull
    @NotBlank
    private String state;

    @NotNull
    @NotBlank
    private String country;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{5,20}$",
            message = "must be 5 to 20 digits only.")
    private String zipCode;

    @NotNull
    @Length(max = 15)
    private String phone;

    private Boolean isActive;

    private List<RoomDTO> rooms;

}
