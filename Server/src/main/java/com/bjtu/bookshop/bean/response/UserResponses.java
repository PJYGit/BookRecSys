package com.bjtu.bookshop.bean.response;

import com.bjtu.bookshop.bean.response.CommonResponses.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserResponses {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse implements StateHolder {
        int state;
        int uid;
        String token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogoutResponse implements StateHolder {
        int state;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterResponse implements StateHolder {
        int state;
        int uid;
        String token;
    }

}
