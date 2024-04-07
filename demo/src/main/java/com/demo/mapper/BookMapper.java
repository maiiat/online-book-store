package com.demo.mapper;

import com.demo.config.MapperConfig;
import com.demo.dto.BookDto;
import com.demo.dto.CreateBookRequestDto;
import com.demo.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);
}
