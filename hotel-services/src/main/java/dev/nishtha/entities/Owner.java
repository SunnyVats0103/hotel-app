package dev.nishtha.entities;

import dev.nishtha.entities.common.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "owners")
public class Owner extends UserEntity {

    @OneToMany(mappedBy = "owner")
    @ToString.Exclude
    private List<Hotel> hotel;

}
