package com.bjtu.bookshop.response;

import java.util.List;

import lombok.Data;

@Data
public class ListResponse<T> implements Response {
    private List<T> list;
    private int state;

    public ListResponse(List<T> list, int state) {
        this.list = list;
        this.state = state;
    }
}
