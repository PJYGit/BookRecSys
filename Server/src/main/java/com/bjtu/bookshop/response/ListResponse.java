package com.bjtu.bookshop.response;

import java.util.List;

import lombok.Data;

@Data
public class ListResponse<T> implements Response {
    private List<T> list;

    public ListResponse(List<T> list) {
        this.list = list;
    }
}