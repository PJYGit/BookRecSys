package com.bjtu.bookshop.bean.request;

import com.alibaba.fastjson.JSON;
import com.bjtu.bookshop.bean.request.CommonRequests.UserAuthorization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

}
