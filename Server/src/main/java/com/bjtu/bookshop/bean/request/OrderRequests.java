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

    @Data
    @AllArgsConstructor
    public static class getInfoRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int cid;
    }

    @Data
    @AllArgsConstructor
    public static class operateRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int cid;
        @NotNull int opcode;
    }
}
