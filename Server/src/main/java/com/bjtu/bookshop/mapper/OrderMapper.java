package com.bjtu.bookshop.mapper;

import com.bjtu.bookshop.bean.db.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {
    @Insert("insert into order_info(uid,bid,cnt,code) values(#{uid}, #{bid}, #{cnt}, #{code})")
    void addNewOrderIntoOrderInfo(int uid, int bid, int cnt, int code);

    @Select("select * from order_info where uid = #{uid} and code = #{type}")
    List<OrderInfo> getAllOrderWithUIDAndType(int uid, int type);

    @Select("select * from order_info where uid = #{uid} and cid = #{cid}")
    OrderInfo getOrderInfoWithUID(int uid, int cid);

    @Update("update order_info set code = op where uid = #{uid} and cid = #{cid}")
    void updateOrderInfoCode(int uid, int cid, int op);
}
