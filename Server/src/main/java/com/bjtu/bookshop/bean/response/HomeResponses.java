package com.bjtu.bookshop.bean.response;

import com.bjtu.bookshop.bean.db.BookTag;
import com.bjtu.bookshop.bean.middle.SimpleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HomeResponses {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TopResponse {
        int state;
        List<elm> list;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class elm{
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

}
