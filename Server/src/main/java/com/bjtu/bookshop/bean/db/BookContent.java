package com.bjtu.bookshop.bean.db;

import lombok.Data;

@Data
public class BookContent {
    private int cid;
    private int bid;
    private int uid;
    private String mark;
    private String content;
}