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
    public static class PersonRequest implements UserAuthorization {
        int uid;
        String token;
    }



}
