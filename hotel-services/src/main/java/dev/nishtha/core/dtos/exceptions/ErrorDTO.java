package dev.nishtha.core.dtos.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class ErrorDTO {

    @Getter
    private Integer errorCode;
    private String errorMessage;
    private String errorDescription;
    private String errorDateTime;

}
