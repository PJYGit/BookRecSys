package com.bjtu.bookshop.bean.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PicResponses {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PicUploadResponse {
        int state;
        String url;
    }
}
