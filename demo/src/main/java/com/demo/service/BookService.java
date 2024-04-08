package com.demo.service;

import com.demo.dto.BookDto;
import com.demo.dto.CreateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto createBookRequestDto);

    List<BookDto> findAll();

    BookDto findById(Long id);
}
