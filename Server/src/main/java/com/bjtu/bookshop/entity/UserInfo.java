package com.bjtu.bookshop.entity;

import lombok.Data;

@Data
public class UserInfo {
    private int uid;
    private String urn;
    private String nickname;
    private long regtime;
    private String head;
    private String viprate;
    private String baned;
    private String money;
}