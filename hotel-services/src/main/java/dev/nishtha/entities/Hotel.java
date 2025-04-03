package dev.nishtha.entities;

import dev.nishtha.entities.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @ToString.Exclude
    private Owner owner;

    @Column(name = "hotel_name", length = 50)
    private String name;

    @Column(name = "email", length = 150)
    private String email;

    private String address;

    private String city;

    private String state;

    private String country;

    @Column(name = "postal_code", length = 20)
    private String zipCode;

    @Column(name = "reference", length = 15, unique = true)
    private String phone;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY,
            mappedBy = "hotel")
    @ToString.Exclude
    private List<Room> rooms;

}
