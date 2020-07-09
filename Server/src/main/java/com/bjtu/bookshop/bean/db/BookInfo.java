package com.bjtu.bookshop.bean.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo {
    private int bid;
    private String name;
    private String author;
    private int sid;
    private String content;
    private String pic;
    private String sales;
    private String remain;
    private int price;
}
