package com.bjtu.bookshop.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class OrderResponses {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class getListResponse {
        int state;
        List<elm> items;

        @Data
        @AllArgsConstructor
        public static class elm {
            int cid;
            int type;
            int sid;
            String sname;
            String pic;
            List<smp> items;

            @Data
            @AllArgsConstructor
            public static class smp {
                String name;
                int cnt;
            }
        }
    }
}
