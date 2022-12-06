package io.kimmking.cache.controller;

import io.kimmking.cache.entity.Author;
import io.kimmking.cache.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class BookstoreController {

    @Autowired
    AuthorService bookstoreService;

    @RequestMapping("/author/find")
    Author find(Long id) {
        return bookstoreService.find(id);
    }
}