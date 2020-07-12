package com.bjtu.bookshop.bean.middle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HardBook{
    int bid;
    int sid;
    String innerTid;
    List<Integer> tid;
    String bname;
    String sname;
    String author;
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