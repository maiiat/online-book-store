package com.demo;

import com.demo.model.Book;
import com.demo.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book kobzar = new Book();
                kobzar.setAuthor("Shevchenko");
                kobzar.setTitle("Kobzar");
                kobzar.setDescription("The list of poems");
                kobzar.setIsbn("1234567890");
                kobzar.setPrice(BigDecimal.valueOf(30));
                kobzar.setCoverImage("https//:someimage.png");

                Book fiftyShadesOfGray = new Book();
                fiftyShadesOfGray.setAuthor("E. L. James");
                fiftyShadesOfGray.setTitle("Fifty Shades trilogy");
                fiftyShadesOfGray.setDescription("Erotic romance novel");
                fiftyShadesOfGray.setIsbn("1234567891");
                fiftyShadesOfGray.setPrice(BigDecimal.valueOf(20));
                fiftyShadesOfGray.setCoverImage("https//:someimage2.png");

                bookService.save(kobzar);
                bookService.save(fiftyShadesOfGray);
                System.out.println("Books list: " + bookService.findAll());

            }
        };
    }
}
