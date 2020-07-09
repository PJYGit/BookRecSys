package com.bjtu.bookshop.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class UserRequests {

    public interface UserAuthorization {
        int getUid();
        String getToken();
    }

    @Data
    @AllArgsConstructor
    public static class LoginRequest{
        @NotNull String urn;
        @NotNull String pw;
    }

    @Data
    @AllArgsConstructor
    public static class LogoutRequest implements UserAuthorization{
        @NotNull int uid;
        @NotNull String token;
    }

}
