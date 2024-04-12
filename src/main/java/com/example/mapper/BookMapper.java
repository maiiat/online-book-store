package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.BookDto;
import com.example.dto.CreateBookRequestDto;
import com.example.dto.UpdateBookRequestDto;
import com.example.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto createBookRequestDto);

    Book updateEntityFromUpdateRequestDto(UpdateBookRequestDto updateBookRequestDto,
                                          @MappingTarget Book entity);
}
