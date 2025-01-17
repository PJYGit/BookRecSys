package com.bjtu.bookshop.mapper;

import com.bjtu.bookshop.bean.db.OrderContent;
import com.bjtu.bookshop.bean.db.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderMapper {
    @Insert("insert into order_info(uid, sid, money, address) values(#{uid}, #{sid}, #{money}, #{address})")
    @Options(useGeneratedKeys = true, keyProperty = "cid")
    void addNewOrderIntoOrderInfo(OrderInfo orderInfo);

    @Insert("insert into order_content(cid, bid, cnt, money) values(#{cid}, #{bid}, #{cnt}, #{money})")
    void addNewOrderIntoOrderContent(OrderContent orderContent);

    @Select("select * from order_info where uid = #{uid} and type = #{type}")
    List<OrderInfo> getAllOrderWithUIDAndType(int uid, int type);

    @Select("select * from order_info where uid = #{uid}")
    List<OrderInfo> getAllOrderWithUID(int uid);

    @Select("select * from order_info where sid = #{sid} and type = #{type}")
    List<OrderInfo> getAllOrderWithSIDAndType(int sid, int type);

    @Select("select * from order_info where sid = #{sid}")
    List<OrderInfo> getAllOrderWithSID(int sid);

    @Select("select * from order_info where cid = #{cid}")
    OrderInfo getOrderInfoWithCID(int cid);

    @Update("update order_info set type = #{type} where cid = #{cid}")
    void updateOrderInfoType(int cid, int type);

    @Update("update order_info set money = #{money} where cid = #{cid}")
    void updateOrderInfoMoney(int cid, int money);

    @Select("select * from order_content where cid = #{cid} ")
    List<OrderContent> getAllOrderContentWithCID(int cid);
}
