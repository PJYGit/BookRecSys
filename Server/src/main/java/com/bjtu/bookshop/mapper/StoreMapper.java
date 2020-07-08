package com.bjtu.bookshop.mapper;


import java.util.List;

import com.bjtu.bookshop.entity.StoreInfo;
import com.bjtu.bookshop.entity.StoreManage;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StoreMapper {
    @Select("select * from store_info where sid = #{sid}")
    StoreInfo getStoreInfoWithSID(int sid);

    @Select("select * from store_info limit #{start}, #{end}")
    List<StoreInfo> getStoreListWithPage(int start, int end);

    @Select("select * from store_manage where sid = #{sid}")
    List<StoreManage> getStoreManagerWithSID(int sid);
}