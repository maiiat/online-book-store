package com.example.mapper;

import com.example.config.MapperConfig;
import com.example.dto.book.BookDto;
import com.example.dto.book.BookDtoWithoutCategoryIds;
import com.example.dto.book.CreateUpdateBookRequestDto;
import com.example.exception.EntityNotFoundException;
import com.example.model.Book;
import com.example.model.Category;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    @Mapping(target = "categoryIds", ignore = true)
    BookDto toDto(Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @Mapping(target = "categories", ignore = true)
    Book toModel(CreateUpdateBookRequestDto createUpdateBookRequestDto);

    Book updateEntityFromUpdateRequestDto(CreateUpdateBookRequestDto updateBookRequestDto,
                                          @MappingTarget Book entity);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> collect = book
                .getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        bookDto.setCategoryIds(collect);
    }

    @AfterMapping
    default void setCategoriesBook(@MappingTarget Book book, CreateUpdateBookRequestDto bookDto) {
        book.setCategories(bookDto
                .getCategoryIds()
                .stream()
                .map(Category::new)
                .collect(Collectors.toSet()));
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        return Optional.ofNullable(id)
            .map(Book::new)
            .orElseThrow(() -> new EntityNotFoundException("Book is not found"));
    }
}
