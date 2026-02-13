package com.portability.users_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.portability.users_service.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    Customer findByEmail(String email);

    Customer findByPhoneNumber(String phoneNumber);

}
