package com.example.service.impl;

import com.example.dto.category.CategoryDto;
import com.example.dto.category.CreateUpdateCategoryRequestDto;
import com.example.exception.CategoryAlreadyExistException;
import com.example.exception.EntityNotFoundException;
import com.example.mapper.CategoryMapper;
import com.example.model.Category;
import com.example.repository.category.CategoryRepository;
import com.example.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryMapper.toDto(categoryRepository.findAll(pageable).getContent());
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = findCategoryById(id);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CreateUpdateCategoryRequestDto createRequestDto) {
        checkIfCategoryNameAlreadyExists(createRequestDto.name());
        Category category = categoryMapper.toModel(createRequestDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    public CategoryDto updateById(
            Long id, CreateUpdateCategoryRequestDto updateCategoryRequestDto) {
        Category category = findCategoryById(id);
        String requestName = updateCategoryRequestDto.name();
        if (!category.getName().equals(requestName)) {
            checkIfCategoryNameAlreadyExists(requestName);
            category.setName(requestName);
        }
        category.setDescription(updateCategoryRequestDto.description());
        Category updatedCategory = categoryRepository
                .save(category);
        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        Category category = findCategoryById(id);
        categoryRepository.deleteById(category.getId());

    }

    private void checkIfCategoryNameAlreadyExists(String categoryName) {
        if (categoryRepository.existsCategoriesByNameIgnoreCase(categoryName)) {
            throw new CategoryAlreadyExistException("Such category already exist: " + categoryName);
        }
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                    "Can't find category by id: " + id));
    }
}
