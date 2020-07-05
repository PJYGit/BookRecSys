package com.bjtu.bookshop.entity;

import lombok.Data;

@Data
public class Role {
    private int role_id;
    private String name;
    private String code;
    private String description;
    private String created;
    private String updated;
}