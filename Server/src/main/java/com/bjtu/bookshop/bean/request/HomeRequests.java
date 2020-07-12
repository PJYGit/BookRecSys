package com.bjtu.bookshop.bean.request;

import com.bjtu.bookshop.bean.request.CommonRequests.UserAuthorization;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

public class HomeRequests {

    @Data
    @AllArgsConstructor
    public static class TopRequest{
    }

    @Data
    @AllArgsConstructor
    public static class NewRequest{
    }

    @Data
    @AllArgsConstructor
    public static class PersonRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
    }

    @Data
    @AllArgsConstructor
    public static class TypeBookRequest {
        @NotNull int tid;
        Integer page;
    }

    @Data
    @AllArgsConstructor
    public static class SearchRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        Integer tid;
        @NotNull String word;
        Double rangemin;
        Double rangemax;
        Integer order;
    }

}
