package com.bjtu.bookshop.entity;

import lombok.Data;

@Data
public class UserInfo {
    private int uid;
    private String urn;
    private String nickname;
    private long regtime;
    private String head;
    private double viprate;
    private int baned;
    private String money;
    private int role;
}
