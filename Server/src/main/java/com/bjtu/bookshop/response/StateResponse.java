package com.bjtu.bookshop.response;

import lombok.Data;

@Data
public class StateResponse implements Response {
    private int state;
    public StateResponse(int state) {
        this.state = state;
    }
}