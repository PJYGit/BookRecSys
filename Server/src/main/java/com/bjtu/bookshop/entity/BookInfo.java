package com.bjtu.bookshop.entity;

import lombok.Data;

@Data
public class BookInfo {
    private int bid;
    private String name;
    private String author;
    private int sid;
    private String content;
    private String pic;
    private String sales;
    private String remain;
    private int tid;
}
