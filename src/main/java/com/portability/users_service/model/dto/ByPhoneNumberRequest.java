package com.portability.users_service.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ByPhoneNumberRequest {
    @NotBlank
    private String phoneNumber;
}
