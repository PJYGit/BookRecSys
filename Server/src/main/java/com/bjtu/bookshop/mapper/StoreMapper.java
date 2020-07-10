package com.bjtu.bookshop.mapper;

import java.util.List;

import com.bjtu.bookshop.bean.db.StoreInfo;
import com.bjtu.bookshop.bean.db.StoreManage;

import com.bjtu.bookshop.bean.response.ShopResponses.*;
import com.bjtu.bookshop.bean.response.UserResponses;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StoreMapper {

    @Select("select * from store_info limit #{start}, #{end}")
    List<StoreInfo> getStoreListWithPage(int start, int end);

    //////////////////////

    @Select("select * from store_info where sid = #{sid}")
    StoreInfo getStoreInfoWithSID(int sid);

    @Select("select s.uid,nickname as name from " +
            "store_manage as s left join user_info as u " +
            "on s.uid = u.uid  where sid = #{sid}")
    List<GetInfoResponse.uel> getStoreManagerWithSID(int sid);

    @Select("select sid,name,head as pic,mark from store_info where name LIKE CONCAT('%',#{name},'%')")
    List<SearchResponse.elm> searchStoreInfo(String name);

    @Select("select count(*) from store_info")
    int getShopCnt();

    @Select("select sid,s.name,uid as bossid,u.nickname as bossname,content,code,s.head,mark " +
            "from store_info as s left join user_info as u on s.boss=u.uid " +
            " limit #{page}, 20")
    List<ManageListResponse.elm> getShopList(int page, boolean real);
    default List<ManageListResponse.elm> getShopList(int page){
        return getShopList(( page - 1 ) * 20,true);
    }
}