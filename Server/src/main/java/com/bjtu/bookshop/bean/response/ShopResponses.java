package com.bjtu.bookshop.bean.response;

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

}
