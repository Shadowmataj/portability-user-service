package com.portability.users_service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to register a new customer")
public record CustomerRequest(
    @Schema(description = "Customer's first name", example = "John")
    String firstName,
    @Schema(description = "Customer's last name", example = "Doe")
    String lastName,
    @Schema(description = "Customer's email address", example = "john.doe@email.com")
    String email,
    @Schema(description = "Customer's phone number", example = "555-1234")
    String phoneNumber
) {}
