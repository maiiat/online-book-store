package com.example.repository.category;

import com.example.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>,
        JpaSpecificationExecutor<Category> {
    boolean existsCategoriesByNameIgnoreCase(String name);
}
