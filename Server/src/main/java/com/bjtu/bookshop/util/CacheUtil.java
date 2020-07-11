package com.bjtu.bookshop.util;

public class CacheUtil {

    public static class TimeCache<T> {
        long delta;
        long lastUpdate;
        T value;
        public void update(T newValue){
            value = newValue;
            lastUpdate = NumberUtil.getUnixTimestamp();
        }
        public boolean available(){
            return NumberUtil.getUnixTimestamp() - lastUpdate <= delta;
        }
        public T get(){
            return value;
        }
    }

}
