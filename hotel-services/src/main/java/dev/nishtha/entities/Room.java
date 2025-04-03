package dev.nishtha.entities;

import dev.nishtha.entities.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rooms")
public class Room extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(name = "is_booked", columnDefinition = "boolean default false")
    private Boolean isBooked;

    private String roomType;

    @Column(name = "room_number", nullable = false)
    @NotNull
    private Integer roomNumber;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(length = 12)
    private Integer capacity;

    @Column(length = 450)
    private String description;

}