package com.example.service;

import com.example.dto.BookDto;
import com.example.dto.CreateBookRequestDto;
import com.example.dto.UpdateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);

    BookDto updateById(Long id, UpdateBookRequestDto updateBookRequestDto);

    void deleteById(Long id);
}

