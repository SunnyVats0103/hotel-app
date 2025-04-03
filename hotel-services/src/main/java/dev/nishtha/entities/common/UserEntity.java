package dev.nishtha.entities.common;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@MappedSuperclass
public class UserEntity extends BaseEntity {

    protected String name;
    protected String email;
    private String phone;
    protected String password;
    @ElementCollection(fetch = FetchType.EAGER)
    protected Set<String> role;

}
