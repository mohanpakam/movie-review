package dev.mpakam.cinecritic.exception;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends Throwable{
    private int httpStatusCode;
    private int errorCode;
    private String errorMessage;
}
