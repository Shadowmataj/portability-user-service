package com.portability.users_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portability.users_service.model.Customer;
import com.portability.users_service.model.dto.CustomerFilterRequest;
import com.portability.users_service.model.dto.CustomerRequest;
import com.portability.users_service.model.dto.CustomerResponse;
import com.portability.users_service.model.dto.PagedResponse;
import com.portability.users_service.repo.CustomerRepo;
import com.portability.users_service.repo.CustomerSpecification;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo repo;

    public ResponseEntity<CustomerResponse> registerCustomer(
            CustomerRequest customerRequest) {

        Customer customer = repo.findByEmail(customerRequest.email());

        // if (!(customer == null)) {
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // }
        customer = new Customer();
        customer.setFirstName(customerRequest.firstName());
        customer.setLastName(customerRequest.lastName());
        customer.setEmail(customerRequest.email());
        customer.setPhoneNumber(customerRequest.phoneNumber());
        Customer savedCustomer = repo.save(customer);

        return new ResponseEntity<>(mapToResponse(savedCustomer), HttpStatus.CREATED);
    }

    public ResponseEntity<CustomerResponse> getCustomerById(Long id) {
        Customer customer = repo.findById(id)
                .orElse(null);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerResponse customerResponse = mapToResponse(customer);

        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    public ResponseEntity<CustomerResponse> getCustomerByEmail(String email) {
        Customer customer = repo.findByEmail(email);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerResponse customerResponse = mapToResponse(customer);

        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    public ResponseEntity<CustomerResponse> getCustomerByPhoneNumber(String phoneNumber) {
        Customer customer = repo.findByPhoneNumber(phoneNumber);

        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerResponse customerResponse = mapToResponse(customer);

        return new ResponseEntity<>(customerResponse, HttpStatus.OK);
    }

    public PagedResponse<CustomerResponse> getCustomers(
            CustomerFilterRequest filter, Pageable pageable) {

        Page<Customer> customerPage;

        if (filter == null || isFilterEmpty(filter)) {
            customerPage = repo.findAll(pageable);
        } else {
            customerPage = repo.findAll(CustomerSpecification.filterBy(filter), pageable);
        }

        List<CustomerResponse> customersResponses = customerPage.getContent().stream()
                .map(customer -> mapToResponse(customer))
                .collect(Collectors.toList());

        return PagedResponse.<CustomerResponse>builder()
                .content(customersResponses)
                .page(customerPage.getNumber())
                .size(customerPage.getSize())
                .totalElements(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .last(customerPage.isLast())
                .first(customerPage.isFirst())
                .numberOfElements(customerPage.getNumberOfElements())
                .empty(customerPage.isEmpty())
                .build();
    }

    private boolean isFilterEmpty(CustomerFilterRequest filter) {
        return (filter.getSearch() == null || filter.getSearch().trim().isEmpty())
                && (filter.getFirstName() == null || filter.getFirstName().trim().isEmpty())
                && (filter.getLastName() == null || filter.getLastName().trim().isEmpty())
                && (filter.getEmail() == null || filter.getEmail().trim().isEmpty());
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
}
