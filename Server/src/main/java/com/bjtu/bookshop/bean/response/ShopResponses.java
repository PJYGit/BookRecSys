package com.bjtu.bookshop.bean.response;

import com.bjtu.bookshop.bean.middle.SimpleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ShopResponses {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GetInfoResponse {
        int state;
        String name;
        SimpleUser boss;
        List<SimpleUser> manager;
        String content;
        int code;
        String head;
        double mark;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookListResponse {
        int state;
        List<book> books;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class book{
            int bid;
            int sid;
            String innerTid;
            List<Integer> tid;
            String bname;
            String sname;
            String auther;
            String content;
            String pic;
            double mark;
            int sales;
            int remain;
            double price;

            public void trans(){
                if(innerTid == null) {
                    tid = new ArrayList<Integer>();
                }else {
                    tid = Arrays.stream(innerTid.split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                }

            }

        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookInfoResponse {
        int state;
        int bid;
        int sid;
        String innerTid;
        List<Integer> tid;
        String bname;
        String sname;
        String auther;
        String content;
        String pic;
        double mark;
        int sales;
        int remain;
        double price;

        public void trans(){
            if(innerTid == null) {
                tid = new ArrayList<Integer>();
            }else {
                tid = Arrays.stream(innerTid.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
            }

        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SearchResponse {
        int state;
        List<elm> shops;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class elm{
            int sid;
            String name;
            String pic;
            double mark;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManageListResponse {
        int state;
        int pageCnt;
        List<elm> shops;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class elm {
            int sid;
            int state;
            String name;
            int bossid;
            String bossname;
            String content;
            int code;
            String head;
            double mark;
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ManageGetInfoResponse {
        int state;
        int uid;
        int sid;
        String name;
        SimpleUser boss;
        List<SimpleUser> manager;
        String content;
        int code;
        String head;
        double mark;
    }

}
