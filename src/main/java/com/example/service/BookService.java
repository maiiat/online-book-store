package com.example.service;

import com.example.dto.BookDto;
import com.example.dto.BookSearchParameters;
import com.example.dto.CreateUpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateUpdateBookRequestDto createUpdateBookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    BookDto updateById(Long id, CreateUpdateBookRequestDto createUpdateBookRequestDto);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters bookSearchParameters);

    public boolean isIsbnExists(String isbn);

}
