package com.portability.users_service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a new portability request")
public record CreatePortabilityRequest(
    @Schema(description = "Phone number to be ported", example = "555-1234")
    String phoneNumber,
    @Schema(description = "Associated customer ID", example = "1")
    Long customerId
) {}
