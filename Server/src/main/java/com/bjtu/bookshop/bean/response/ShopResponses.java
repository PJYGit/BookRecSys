package com.bjtu.bookshop.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class ShopResponses {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetInfoResponse {
        int state;
        String name;
        uel boss;
        List<uel> manager;
        String content;
        int code;
        String head;
        double mark;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class uel{
            int uid;
            String name;
        }

    }

}
