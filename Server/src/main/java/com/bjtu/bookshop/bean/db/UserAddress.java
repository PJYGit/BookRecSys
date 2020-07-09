package com.bjtu.bookshop.bean.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {
    int uid;
    int plid;
    String title;
    String content;
    String name;
    String phone;
    int selected;
}
