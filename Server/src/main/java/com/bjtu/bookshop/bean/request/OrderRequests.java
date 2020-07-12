package com.bjtu.bookshop.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class OrderRequests {
    @Data
    @AllArgsConstructor
    public static class getListRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int type;
    }
}
