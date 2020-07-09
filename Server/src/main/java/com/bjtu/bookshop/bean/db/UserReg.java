package com.bjtu.bookshop.bean.db;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserReg {
    private int uid;
    private String urn;
    private String pwd;
    private String salt;
}
