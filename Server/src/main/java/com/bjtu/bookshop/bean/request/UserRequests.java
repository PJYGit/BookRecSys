package com.bjtu.bookshop.bean.request;

import com.bjtu.bookshop.bean.request.CommonRequests.*;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

public class UserRequests {

    @Data
    @AllArgsConstructor
    public static class LoginRequest{
        @NotNull String urn;
        @NotNull String pw;
    }

    @Data
    @AllArgsConstructor
    public static class LogoutRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
    }

    @Data
    @AllArgsConstructor
    public static class RegisterRequest {
        @NotNull String urn;
        String uname;
        @NotNull String code;
        @NotNull String psw;
    }

    @Data
    @AllArgsConstructor
    public static class PhoneRequest {
        @NotNull String urn;
    }

    @Data
    @AllArgsConstructor
    public static class InfoRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
    }

}
