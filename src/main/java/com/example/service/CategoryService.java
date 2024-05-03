package com.example.service;

import com.example.dto.category.CategoryDto;
import com.example.dto.category.CreateUpdateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto findById(Long id);

    CategoryDto save(CreateUpdateCategoryRequestDto createRequestDto);

    CategoryDto updateById(Long id, CreateUpdateCategoryRequestDto updateRequestDto);

    void deleteById(Long id);
}
