package com.example.RegistrationLoginPage.dto;

import lombok.Data;

@Data
public class CommonResponseDTO {
    private boolean status;
    private String message;
    private Object data;

    // Constructor with status and message
    public CommonResponseDTO(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    // Constructor with status, message, and data (optional)
    public CommonResponseDTO(boolean status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
