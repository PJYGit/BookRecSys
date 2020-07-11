package com.bjtu.bookshop.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class CarResponses {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getListResponse {
        int state;
        List<elm> items;

        @Data
        @AllArgsConstructor
        public static class elm {
            int bid;
            String bname;
            int sid;
            String sname;
            String pic;
            int cnt;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class addBookResponse {
        int state;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class setListResponse {
        int state;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class submitResponse {
        int state;
        int cid;
    }
}
