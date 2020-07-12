package com.bjtu.bookshop.bean.response;

import com.bjtu.bookshop.bean.db.BookTag;
import com.bjtu.bookshop.bean.middle.HardBook;
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
        List<HardBook> list;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PersonResponse {
        int state;
        List<HardBook> list;
    }

}
