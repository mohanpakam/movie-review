package dev.mpakam.cinecritic.dto;

import lombok.Builder;

@Builder
public record ErrorResponseDto(int httpStatusCode, int errorCode,String errorMessage){}
