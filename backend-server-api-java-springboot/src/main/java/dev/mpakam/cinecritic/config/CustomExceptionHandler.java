package dev.mpakam.cinecritic.config;

import dev.mpakam.cinecritic.dto.ErrorResponseDto;
import dev.mpakam.cinecritic.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleReviewAlreadyExistsException(CustomException ex){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .httpStatusCode(ex.getHttpStatusCode())
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorMessage()).build();
        return ResponseEntity.status(errorResponseDto.httpStatusCode()).body(errorResponseDto);
    }
}
