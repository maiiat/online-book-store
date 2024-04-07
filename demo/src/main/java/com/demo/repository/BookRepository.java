package com.demo.repository;

import com.demo.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List findAll();
}

