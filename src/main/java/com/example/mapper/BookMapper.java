package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.book.BookDto;
import com.example.dto.book.CreateUpdateBookRequestDto;
import com.example.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateUpdateBookRequestDto createUpdateBookRequestDto);

    Book updateEntityFromUpdateRequestDto(CreateUpdateBookRequestDto updateBookRequestDto,
                                          @MappingTarget Book entity);
}
