package com.bjtu.bookshop.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserResponses {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse{
        int state;
        int uid;
        String token;
        public static LoginResponse FailWith(int _state){
            return new LoginResponse(){{state = _state;}};
        }
    }

}
