package com.bjtu.bookshop.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    @Data
    @AllArgsConstructor
    public static class commentRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int cid;
        @NotNull List<cmt> items;

        @Data
        @AllArgsConstructor
        public static class cmt {
            @NotNull int bid;
            @NotNull int mark;
            @NotNull String comment;
        }
    }

    @Data
    @AllArgsConstructor
    public static class createRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String address;
        @NotNull int bid;
        @NotNull int cnt;
    }
}