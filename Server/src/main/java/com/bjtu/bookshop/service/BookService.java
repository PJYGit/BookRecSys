package com.bjtu.bookshop.service;

import com.bjtu.bookshop.mapper.BookMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
}
