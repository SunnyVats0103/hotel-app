package dev.nishtha.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private String userName;
    private String password;

}
