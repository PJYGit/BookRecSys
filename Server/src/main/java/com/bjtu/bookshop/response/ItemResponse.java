package com.bjtu.bookshop.response;

import lombok.Data;

@Data
public class ItemResponse<T> implements Response {

    private T item;
    private int state;

    public ItemResponse(T item, int state) {
        this.item = item;
        this.state = state;
    }
}