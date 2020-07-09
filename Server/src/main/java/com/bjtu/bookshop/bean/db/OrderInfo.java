package com.bjtu.bookshop.bean.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 李高丞
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private int cid;
    private int uid;
    private int sid;
    private int state;
    private int money;
    private String content;
}
