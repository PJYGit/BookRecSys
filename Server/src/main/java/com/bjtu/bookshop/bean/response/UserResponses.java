package com.bjtu.bookshop.bean.response;

import com.bjtu.bookshop.bean.response.CommonResponses.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserResponses {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse {
        int state;
        int uid;
        String token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LogoutResponse {
        int state;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterResponse {
        int state;
        int uid;
        String token;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhoneResponse {
        int state;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetInfoResponse {
        int state;
        int uid;
        String urn;
        String nickname;
        int role;
        String head;
        long regtime;
        double vipRate;
        double money;
        List<loc> address;
        List<elm> managed;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class loc{
            String title;
            String content;
            String name;
            String phone;
            int selected;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class elm{
            int sid;
            String sname;
            int boss;
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SetInfoResponse {
        int state;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManageListResponse {
        int state;
        int pagesize;
        List<elm> list;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class elm {
            int uid;
            String token;
            String nickname;
            long regtime;
            double vipRate;
            int baned;
            int role;
            double money;
        }

    }


}
