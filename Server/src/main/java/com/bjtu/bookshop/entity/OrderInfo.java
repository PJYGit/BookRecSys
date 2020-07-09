package com.bjtu.bookshop.entity;

import lombok.Data;

/**
 * @author 李高丞
 * @version 1.0
 */
@Data
public class OrderInfo {
    private int cid;
    private int uid;
    private int bid;
    private int cnt;
    private int code;
}
