package com.bjtu.bookshop.bean.request;

import com.bjtu.bookshop.bean.request.CommonRequests.UserAuthorization;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ShopRequests {

    @Data
    @AllArgsConstructor
    public static class GetInfoRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int sid;
    }

    @Data
    @AllArgsConstructor
    public static class BookListRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int sid;
    }

    @Data
    @AllArgsConstructor
    public static class BookInfoRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int bid;
        //@NotNull int sid;
    }

    @Data
    @AllArgsConstructor
    public static class SearchRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String word;
    }

    @Data
    @AllArgsConstructor
    public static class ManageListRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        Integer page;
    }

    @Data
    @AllArgsConstructor
    public static class ManageGetInfoRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int sid;
    }

    @Data
    @AllArgsConstructor
    public static class ManageSetInfoRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        Integer sid;
        List<Integer> managers;
        String content;
        Integer code;
        String head;
    }

    @Data
    @AllArgsConstructor
    public static class ManageAddBookRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int sid;
        @NotNull List<Integer> tid;
        @NotNull String bname;
        @NotNull String author;
        @NotNull String content;
        @NotNull String pic;
        @NotNull int remain;
        @NotNull double price;
    }

    @Data
    @AllArgsConstructor
    public static class ManageDelBookRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int bid;
    }

    @Data
    @AllArgsConstructor
    public static class ManageSetBookRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int bid;
        List<Integer> tid;
        String bname;
        String author;
        String content;
        String pic;
        Integer remain;
        Double price;
    }

    @Data
    @AllArgsConstructor
    public static class ManageGetUserRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String phone;
    }

    @Data
    @AllArgsConstructor
    public static class TagListRequest {
    }

    @Data
    @AllArgsConstructor
    public static class NewShopRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String name;
        @NotNull String content;
    }
}
