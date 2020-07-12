package com.bjtu.bookshop.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.bjtu.bookshop.bean.request.CommonRequests.*;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CarRequests {
    @Data
    @AllArgsConstructor
    public static class getListRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
    }

    @Data
    @AllArgsConstructor
    public static class addBookRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int bid;
        @NotNull int cnt;
        @NotNull int sid;
    }

    @Data
    @AllArgsConstructor
    public static class setListRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull List<eml> car;

        @Data
        @AllArgsConstructor
        public static class eml {
            @NotNull int bid;
            @NotNull int cnt;
        }
    }

    @Data
    @AllArgsConstructor
    public static class submitRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String address;
        @NotNull List<eml> buy;

        @Data
        @AllArgsConstructor
        public static class eml {
            @NotNull int bid;
            @NotNull int cnt;
        }
    }
}
