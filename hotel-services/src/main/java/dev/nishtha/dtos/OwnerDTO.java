package dev.nishtha.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String role;
    private String password;

}
