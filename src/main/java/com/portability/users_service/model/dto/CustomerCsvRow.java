package com.portability.users_service.model.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "CSV row with basic customer information")
public record CustomerCsvRow(
    @Schema(description = "Unique customer ID")
    Long id,
    @Schema(description = "Customer's first name")
    String firstName,
    @Schema(description = "Customer's last name")
    String lastName,
    @Schema(description = "Customer's email address")
    String email,
    @Schema(description = "Customer's phone number")
    String phoneNumber,
    @Schema(description = "Customer creation date")
    LocalDate createdAt,
    @Schema(description = "Customer last update date")
    LocalDate updatedAt
) {}
