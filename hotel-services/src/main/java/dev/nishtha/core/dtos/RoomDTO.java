package dev.nishtha.core.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDTO {

    private Long id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String hotelId;
    private Boolean isBooked;
    private String roomType;
    private Integer roomNumber;
    private Double price;
    private Integer capacity;
    private String description;

}
