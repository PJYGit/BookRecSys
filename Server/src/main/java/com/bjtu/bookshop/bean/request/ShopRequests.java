package com.bjtu.bookshop.bean.request;

import com.alibaba.fastjson.JSON;
import com.bjtu.bookshop.bean.request.CommonRequests.UserAuthorization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ShopRequests {

    @Data
    @AllArgsConstructor
    public static class GetInfoRequest implements UserAuthorization {
        @NotNull int uid;
        @NotNull String token;
        @NotNull int sid;
    }

}
