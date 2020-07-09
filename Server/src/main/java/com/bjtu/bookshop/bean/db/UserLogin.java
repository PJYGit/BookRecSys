package com.bjtu.bookshop.bean.db;

import lombok.Data;

@Data
public class UserLogin {
    private int uid;
    private String token;
}
