package com.portability.users_service.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.portability.users_service.model.Customer;
import com.portability.users_service.model.dto.CustomerFilterRequest;

import jakarta.persistence.criteria.Predicate;

public class CustomerSpecification {

    /**
     * Create a specification based on filter request
     */
    public static Specification<Customer> filterBy(CustomerFilterRequest filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search across multiple fields
            if (filter.getSearch() != null && !filter.getSearch().trim().isEmpty()) {
                String searchPattern = "%" + filter.getSearch().toLowerCase() + "%";
                Predicate searchPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), searchPattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), searchPattern)
                );
                predicates.add(searchPredicate);
            }

            // Filter by first name
            if (filter.getFirstName() != null && !filter.getFirstName().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("firstName")),
                    "%" + filter.getFirstName().toLowerCase() + "%"
                ));
            }

            // Filter by last name
            if (filter.getLastName() != null && !filter.getLastName().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("lastName")),
                    "%" + filter.getLastName().toLowerCase() + "%"
                ));
            }

            // Filter by email
            if (filter.getEmail() != null && !filter.getEmail().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("email")),
                    "%" + filter.getEmail().toLowerCase() + "%"
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
