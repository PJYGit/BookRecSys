package com.bjtu.bookshop.bean.response;

public class CommonResponses {

    public interface StateHolder {
        void setState(int state);
        default void FailWith(int code){
            setState(code);
        };
    }

}
