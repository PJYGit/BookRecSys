package com.bjtu.bookshop.entity;

import lombok.Data;

@Data
public class UserReg {
    private int uid;
    private String urn;
    private String pwd;
    private String salt;
}