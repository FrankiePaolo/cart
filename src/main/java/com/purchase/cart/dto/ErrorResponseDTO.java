package com.purchase.cart.dto;


import com.purchase.cart.enums.ErrorCode;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {
    private String message;
    private LocalDateTime timestamp;
    private String errorType;
    private String errorCode;

    public ErrorResponseDTO(String message, ErrorCode errorCode) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.errorType = errorCode.name();
        this.errorCode = errorCode.getCode();
    }
}
