package com.bjtu.bookshop.mapper;

import com.bjtu.bookshop.bean.db.CarInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CarMapper {

    @Insert("insert into car_info(uid, bid, cnt, sid) values(#{uid}, #{bid}, #{cnt}, #{sid})")
    void insertIntoCarInfo(int uid, int bid, int cnt, int sid);

    @Select("select * from car_info where uid = #{uid}")
    List<CarInfo> getCarInfoWithUID(int uid);

    @Delete("delete from car_info where uid = #{uid}")
    void deleteCarInfoWithUid(int uid);

    @Delete("delete from car_info where uid = #{uid} and bid = #{bid}")
    void deleteCarInfoWithUIDAndBID(int uid, int bid);
}
