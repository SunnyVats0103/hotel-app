package dev.nishtha.core.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDTO {

    private String username;
    private String password;
    private String role;

}
