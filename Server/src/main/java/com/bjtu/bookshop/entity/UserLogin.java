package com.bjtu.bookshop.entity;

import lombok.Data;

@Data
public class UserLogin {
    private int uid;
    private String token;
}