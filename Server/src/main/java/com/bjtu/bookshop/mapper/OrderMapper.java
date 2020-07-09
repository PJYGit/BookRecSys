package com.bjtu.bookshop.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrderMapper {
    @Insert("insert into order_info(uid,bid,cnt,code) values(#{uid}, #{bid}, #{cnt}, #{code})")
    void addNewOrderIntoOrderInfo(int uid, int bid, int cnt, int code);
}
