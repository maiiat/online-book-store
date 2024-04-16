package com.example.service;

import com.example.dto.BookDto;
import com.example.dto.BookSearchParameters;
import com.example.dto.CreateUpdateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateUpdateBookRequestDto createUpdateBookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateById(Long id, CreateUpdateBookRequestDto createUpdateBookRequestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters bookSearchParameters);

    public boolean isIsbnExists(String isbn);

}
