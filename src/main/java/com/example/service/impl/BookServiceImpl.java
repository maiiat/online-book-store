package com.example.service.impl;

import com.example.dto.book.BookDto;
import com.example.dto.book.BookSearchParameters;
import com.example.dto.book.CreateUpdateBookRequestDto;
import com.example.exception.EntityNotFoundException;
import com.example.exception.IsbnAlreadyExistException;
import com.example.mapper.BookMapper;
import com.example.model.Book;
import com.example.repository.book.BookRepository;
import com.example.repository.book.BookSpecificationBuilder;
import com.example.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateUpdateBookRequestDto createUpdateBookRequestDto) {
        checkIfIsbnExistsOrThrowException(createUpdateBookRequestDto.isbn());
        Book book = bookMapper.toModel(createUpdateBookRequestDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
            .map(bookMapper::toDto)
            .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id: " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public BookDto updateById(Long id, CreateUpdateBookRequestDto updateBookRequestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find book by id: " + id));

        String isbn = updateBookRequestDto.isbn();
        if (!book.getIsbn().equals(isbn)) {
            checkIfIsbnExistsOrThrowException(isbn);
        }
        Book updatedBook = bookRepository
                .save(bookMapper.updateEntityFromUpdateRequestDto(updateBookRequestDto, book));
        return bookMapper.toDto(updatedBook);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters bookSearchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParameters);
        return bookRepository.findAll(bookSpecification).stream()
            .map(bookMapper::toDto)
            .toList();
    }

    private void checkIfIsbnExistsOrThrowException(String isbn) {
        if (bookRepository.existsByIsbn(isbn)) {
            throw new IsbnAlreadyExistException("such ISBN already exist: " + isbn);
        }
    }
}
