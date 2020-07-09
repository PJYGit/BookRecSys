package com.bjtu.bookshop.mapper;

import com.bjtu.bookshop.entity.CarInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CarMapper {

    @Insert("insert into car_info(uid, bid, sid, cnt) values(#{uid}, #{bid}, #{sid}, #{cnt})")
    void insertIntoCarInfo(int uid, int bid, int sid, int cnt);

    @Select("select * from car_info where uid = #{uid}")
    List<CarInfo> getCarInfoWithUID(int uid);

    @Update("update car_info set cnt = #{cnt} where uid = #{uid} and bid = #{bid}")
    void updateCarInfo(int uid, int bid, int cnt);

    @Delete("delete from car_info where uid = #{uid} and bid = #{bid}")
    void deleteCarInfoWithBIDAndCnt(int uid, int bid);
}
