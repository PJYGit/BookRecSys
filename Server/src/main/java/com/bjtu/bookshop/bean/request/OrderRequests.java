package com.bjtu.bookshop.bean.request;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class OrderRequests {
    @Data
    @AllArgsConstructor
    public static class getListRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int type;
    }

    @Data
    @AllArgsConstructor
    public static class getInfoRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int cid;
    }

    @Data
    @AllArgsConstructor
    public static class operateRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int cid;
        @NotNull int opcode;
    }

    @Data
    @AllArgsConstructor
    public static class commentRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int cid;
        @NotNull List<String> items;
        List<cmt> innerItems;

        @Data
        @AllArgsConstructor
        public static class cmt {
            @NotNull int bid;
            @NotNull int mark;
            @NotNull String comment;
        }

        public boolean trans() {
            try {
                innerItems = new ArrayList<>();
                for (String str : items) {
                    if ("".equals(str)) continue;
                    cmt obj = JSON.parseObject(str, cmt.class);
                    if (obj == null) {
                        return false;
                    }
                    innerItems.add(obj);
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    @Data
    @AllArgsConstructor
    public static class createRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String address;
        @NotNull int bid;
        @NotNull int cnt;
    }

    @Data
    @AllArgsConstructor
    public static class manageGetListRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int sid;
        @NotNull int type;
    }

    @Data
    @AllArgsConstructor
    public static class manageOperateRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int cid;
        @NotNull int opcode;
    }

    @Data
    @AllArgsConstructor
    public static class manageSetInfoRequest implements CommonRequests.UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int cid;
        @NotNull double money;
    }
}