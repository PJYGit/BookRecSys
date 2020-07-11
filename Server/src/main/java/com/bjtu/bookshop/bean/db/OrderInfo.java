package com.bjtu.bookshop.bean.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private int cid;
    private int uid;
    private int sid;
    private int state;
    private int cost;
    private String address;
    private String comment;
    int mark;
}
