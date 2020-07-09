package com.bjtu.bookshop.bean.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private int uid;
    private String urn;
    private String nickname;
    private long regtime;
    private String head;
    private int viprate;
    private int baned;
    private String money;
    private int role;
}
