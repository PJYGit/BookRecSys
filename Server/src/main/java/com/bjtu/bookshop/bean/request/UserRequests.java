package com.bjtu.bookshop.bean.request;

import com.alibaba.fastjson.JSON;
import com.bjtu.bookshop.bean.request.CommonRequests.*;

import com.bjtu.bookshop.bean.response.UserResponses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    public static class GetInfoRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
    }

    @Data
    @AllArgsConstructor
    public static class SetInfoRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        String nickname;
        String head;
        //List<loc> address;
        List<String> address;
        List<loc> innerAddress;

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

        public boolean trans(){
            if(address == null) return true;
            try{
                innerAddress = new ArrayList<loc>();
                for(String s : address){
                    if("".equals(s)) continue;
                    loc objs = JSON.parseObject(s, loc.class);
                    if(objs == null) return false;
                    innerAddress.add(objs);
                }
            }catch (Exception e){
                return false;
            }
            return true;
        }
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
    public static class ManageSearchRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String phone;
    }

    @Data
    @AllArgsConstructor
    public static class ManageGetRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int target;
    }

    @Data
    @AllArgsConstructor
    public static class ManageSetRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int target;
        String nickname;
        Double vipRate;
        Integer baned;
        Integer role;
        Double money;
    }

    @Data
    @AllArgsConstructor
    public static class ManageAddRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String urn;
        @NotNull String password;
        @NotNull String nickname;
        @NotNull Double vipRate;
        @NotNull Integer role;
        @NotNull Integer baned;
        @NotNull Double money;
    }

}
