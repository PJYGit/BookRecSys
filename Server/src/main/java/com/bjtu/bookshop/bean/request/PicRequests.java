package com.bjtu.bookshop.bean.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public class PicRequests {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PicUploadRequest implements CommonRequests.UserAuthorization {
        @NonNull int uid;
        @NonNull String token;
        @NonNull MultipartFile pic;
    }

}
