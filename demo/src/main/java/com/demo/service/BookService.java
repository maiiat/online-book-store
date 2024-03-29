package com.demo.service;

import com.demo.model.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List findAll();
}
