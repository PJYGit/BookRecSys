package com.bjtu.bookshop.response;

import lombok.Data;

@Data
public class ItemResponse<T> implements Response {

    private T item;

    public ItemResponse(T item) {
        this.item = item;
    }
}