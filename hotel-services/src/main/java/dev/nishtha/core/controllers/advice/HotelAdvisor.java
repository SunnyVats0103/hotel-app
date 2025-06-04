package dev.nishtha.core.controllers.advice;

import dev.nishtha.core.dtos.exceptions.ErrorDTO;
import dev.nishtha.core.utils.SimpleDateFormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@ControllerAdvice
public class HotelAdvisor {

    private final SimpleDateFormatUtil simpleDateFormatUtil;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                RedirectAttributes rAtt) {

        List<ErrorDTO> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.value())
                    .errorMessage(error.getField())
                    .errorDescription(error.getDefaultMessage())
                    .errorDateTime(simpleDateFormatUtil.formatToUTC(Date.valueOf(LocalDate.now())))
                    .build();
            errors.add(errorDTO);
        });

        rAtt.addFlashAttribute("errors", errors);

        return "redirect:/public/web/hotels/add";
    }

}
