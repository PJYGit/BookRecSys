package com.bjtu.bookshop.bean.request;

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

}
