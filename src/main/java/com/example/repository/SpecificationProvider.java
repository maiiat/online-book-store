package com.example.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    public String getKey();

    public Specification<T> getSpecification(String[] params);
}
