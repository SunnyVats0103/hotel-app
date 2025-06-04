package dev.nishtha.core.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserResponseDTO {

    private String username;
    private String email;
    private Set<String> role;
    private String createdAt;
    private String updatedAt;
    private boolean isActive;
    private boolean isEmailVerified;

}
