package com.bjtu.bookshop.bean.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookContent {
    private int cid;
    private int bid;
    private int uid;
    private String mark;
    private String content;
}