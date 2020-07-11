package com.bjtu.bookshop.bean.request;

public class CommonRequests {

    public interface UserAuthorization {
        int getUid();
        String getToken();
    }

    public interface ShopAuthorization {
        int getUid();
        int getSid();
    }

}
