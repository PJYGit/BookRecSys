package com.bjtu.bookshop.bean.request;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.bjtu.bookshop.bean.request.CommonRequests.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CarRequests {
    @Data
    @AllArgsConstructor
    public static class getListRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
    }

    @Data
    @AllArgsConstructor
    public static class addBookRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int bid;
        @NotNull int cnt;
        @NotNull int sid;
    }

    @Data
    @AllArgsConstructor
    public static class setListRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull List<String> car;
        List<eml> innerCar;

        @Data
        @AllArgsConstructor
        public static class eml {
            @NotNull int bid;
            @NotNull int cnt;
        }

        public boolean trans() {
            try {
                innerCar = new ArrayList<>();
                for (String str : car) {
                    if ("".equals(str)) continue;
                    eml obj = JSON.parseObject(str, eml.class);
                    if (obj == null) {
                        return false;
                    }
                    innerCar.add(obj);
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }

    @Data
    @AllArgsConstructor
    public static class submitRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull String address;
        @NotNull List<String> buy;
        List<eml> innerBuy;

        @Data
        @AllArgsConstructor
        public static class eml {
            @NotNull int bid;
            @NotNull int cnt;
        }

        public boolean trans() {
            try {
                innerBuy = new ArrayList<>();
                for (String str : buy) {
                    if ("".equals(str)) continue;
                    eml obj = JSON.parseObject(str, eml.class);
                    if (obj == null) {
                        return false;
                    }
                    innerBuy.add(obj);
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }
    }
}
