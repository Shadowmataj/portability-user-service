package com.portability.users_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portability.users_service.model.dto.ByEmailRequest;
import com.portability.users_service.model.dto.ByPhoneNumberRequest;
import com.portability.users_service.model.dto.CustomerFilterRequest;
import com.portability.users_service.model.dto.CustomerRequest;
import com.portability.users_service.model.dto.CustomerResponse;
import com.portability.users_service.model.dto.PagedResponse;
import com.portability.users_service.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Customer", description = "API for customer management")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Operation(
            summary = "Filter customers",
            description = "Retrieve a paginated list of customers using filters sent in the request body. Use this endpoint to filter by personal or sensitive information."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Customers retrieved successfully"
    )
    @PostMapping("/customers/filter")
    public ResponseEntity<PagedResponse<CustomerResponse>> filterCustomers(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Filter criteria for customers",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CustomerFilterRequest.class))
            )
            @RequestBody CustomerFilterRequest filter,
            @Parameter(description = "Page number (0-indexed)", example = "0")
            @RequestParam(required = false, defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "20")
            @RequestParam(required = false, defaultValue = "20") int size,
            @Parameter(description = "Sort field", example = "id")
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)", example = "asc")
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        PagedResponse<CustomerResponse> customers = service.getCustomers(filter, pageable);
        return ResponseEntity.ok(customers);
    }

    @Operation(
            summary = "Get customer by ID",
            description = "Retrieve a single customer by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(description = "Customer ID", example = "1")
            @PathVariable Long id) {
        return service.getCustomerById(id);
    }

    @Operation(
            summary = "Get customer by email",
            description = "Retrieve a single customer by their email"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @PostMapping("/customers/by-email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(
            @Valid
            @RequestBody ByEmailRequest emailRequest) {
        return service.getCustomerByEmail(emailRequest.getEmail());
    }

    @Operation(
            summary = "Get customer by phone number",
            description = "Retrieve a single customer by their phone number"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer found successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
        @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @PostMapping("/customers/by-phone")
    public ResponseEntity<CustomerResponse> getCustomerByPhoneNumber(
            @Valid
            @RequestBody ByPhoneNumberRequest phoneNumberRequest) {
        return service.getCustomerByPhoneNumber(phoneNumberRequest.getPhoneNumber());
    }

    @Operation(summary = "Register new customer", description = "Registers a new customer in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Customer registered successfully", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/customer/register")
    public ResponseEntity<CustomerResponse> register(
            @Valid
            @RequestBody CustomerRequest customerRequest) {
        return service.registerCustomer(customerRequest);
    }
}
