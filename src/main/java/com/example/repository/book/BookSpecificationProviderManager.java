package com.example.repository.book;

import com.example.model.Book;
import com.example.repository.SpecificationProvider;
import com.example.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookSpecificationProvider;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookSpecificationProvider.stream()
                .filter(k -> k.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find specification provider by key: "
                        + key));
    }
}
