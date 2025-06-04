package dev.nishtha.core.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRegisterDTO {

    private String name;
    private String email;
    private String password;
    private String phone;

}
