package ars.otus.sonet.advice;

import ars.otus.sonet.configuration.filter.RequestIdFilter;
import ars.otus.sonet.exception.SonetException;
import ars.otus.sonet.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static ars.otus.sonet.model.enums.ErrorCodes.DEFAULT_ERROR_CODE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {SonetException.class})
    public ResponseEntity mdServiceException(SonetException ex) {
        log.error("Ошибка приложения. Код: {}, Ошибка: {}",
                ex.getErrorCode().getInternalErrorCode(),
                ex.getMessage());
        return switch (ex.getErrorCode()) {
            case NOT_FOUND -> ResponseEntity.notFound().build();
            case BAD_CREDENTIALS -> ResponseEntity.badRequest().body(ex.getMessage());
            default -> ResponseEntity.internalServerError().body(
                    ErrorResponse.builder()
                            .code(ex.getErrorCode().getInternalErrorCode())
                            .message(ex.getMessage())
                            .requestId(RequestIdFilter.requestId.get())
                            .build());
        };
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity commonException(Exception ex) {
        log.error("Ошибка приложения времени выполнения. {}",
                ex.getMessage());
        return ResponseEntity.internalServerError().body(
                ErrorResponse.builder()
                        .code(DEFAULT_ERROR_CODE.getInternalErrorCode())
                        .message(ex.getMessage())
                        .requestId(RequestIdFilter.requestId.get())
                        .build());
    }
}
