package com.example.order.exception;

import lombok.Builder;


import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private String error;
    private String message;
    private String timestamp;
}
