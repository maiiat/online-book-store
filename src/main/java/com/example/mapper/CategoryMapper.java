package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.category.CategoryDto;
import com.example.dto.category.CreateUpdateCategoryRequestDto;
import com.example.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CreateUpdateCategoryRequestDto createUpdateCategoryRequestDto);
}
