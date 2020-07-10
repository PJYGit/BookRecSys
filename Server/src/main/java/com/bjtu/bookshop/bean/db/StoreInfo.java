package com.bjtu.bookshop.bean.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreInfo {
    private int sid;
    private String name;
    private int boss;
    private String content;
    private int code;
    private String head;
    private double mark;
}