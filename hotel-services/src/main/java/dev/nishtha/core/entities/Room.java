package dev.nishtha.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    private Date deletedAt;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(name = "is_booked", columnDefinition = "boolean default false")
    private Boolean isBooked;

    private String roomType;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(length = 12)
    private Integer capacity;

    @Column(length = 450)
    private String description;

}