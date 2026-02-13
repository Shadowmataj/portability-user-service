package com.portability.users_service.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Customer filter request")
public class CustomerFilterRequest {

    @Schema(description = "Search term for first name, last name, or email")
    private String search;

    @Schema(description = "Filter by customer's first name")
    private String firstName;

    @Schema(description = "Filter by customer's last name")
    private String lastName;

    @Schema(description = "Filter by customer's email")
    private String email;
}
