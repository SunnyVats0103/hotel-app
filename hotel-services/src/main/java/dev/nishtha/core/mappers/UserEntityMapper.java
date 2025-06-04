package dev.nishtha.core.mappers;

import dev.nishtha.core.dtos.UserRegisterDTO;
import dev.nishtha.core.dtos.UserResponseDTO;
import dev.nishtha.core.entities.UserEntity;
import dev.nishtha.core.utils.SimpleDateFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserEntityMapper {

    private final SimpleDateFormatUtil formatter;

    public UserEntity toEntity(UserRegisterDTO dto) {
        return UserEntity.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .userName(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();
    }

    public UserResponseDTO toResponseDTO(UserEntity entity) {
        return UserResponseDTO.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .role(entity.getRole())
                .createdAt(formatter.formatToUTC(entity.getCreatedAt()))
                .updatedAt(formatter.formatToUTC(entity.getUpdatedAt()))
                .isActive(entity.isEnabled())
                .isEmailVerified(entity.getRegistered() != null && entity.getRegistered())
                .build();
    }

}
