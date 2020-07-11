package com.bjtu.bookshop.mapper;

import java.util.List;

import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.BookTag;
import com.bjtu.bookshop.bean.db.StoreInfo;
import com.bjtu.bookshop.bean.db.StoreManage;

import com.bjtu.bookshop.bean.middle.SimpleUser;
import com.bjtu.bookshop.bean.response.ShopResponses.*;
import com.bjtu.bookshop.bean.response.UserResponses;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface StoreMapper {

    @Select("select * from store_info where sid = #{sid}")
    StoreInfo getStoreInfoWithSID(int sid);

    @Select("select s.uid,nickname as name from " +
            "store_manage as s left join user_info as u " +
            "on s.uid = u.uid  where sid = #{sid}")
    List<SimpleUser> getStoreManagerWithSID(int sid);

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

    @Select("select count(*) from store_manage where sid = ${sid} and uid = ${uid}")
    int checkManager(Integer sid, int uid);

    @Select("select * from store_info where boss = #{uid}")
    StoreInfo getStoreInfoWithBoss(int uid);

    @Delete("delete from store_manage where sid = ${sid}")
    void cleanManager(int sid);

    @Insert("insert into store_manage(sid,uid) values (#{sid},#{uid})")
    void insertManager(int sid, int uid);

    @Update("update store_info set name = #{name}, boss = #{boss}," +
            "content = #{content}, code = #{code}, head = #{head}," +
            "mark = #{mark} where sid = #{sid}")
    void updateStoreInfo(StoreInfo info);

    @Insert("INSERT INTO book_info(name, author, sid, content, pic, sales, remain, price) " +
            "VALUES(#{body.name}, #{body.author}, #{body.sid}, #{body.content}, " +
            "#{body.pic}, #{body.sales}, #{body.remain}, #{body.price} )")
    @Options(useGeneratedKeys = true, keyProperty = "body.bid")
    void InsertBook(@Param("body") BookInfo binfo, boolean real);
    default int InsertBook(BookInfo binfo){
        InsertBook(binfo,true);
        return binfo.getBid();
    }

    @Insert("insert into book_tag(bid,tid) values (#{bid},#{tid})")
    void insertTagForBook(int bid, int tid);

    @Delete("delete from book_info where bid = #{bid} ")
    int removeBook(int bid);

    @Delete("delete from book_tag where bid = #{bid} ")
    void cleanBookTag(int bid);

    @Select("select * from book_info where bid = #{bid}")
    BookInfo getBookWithBid(int bid);

    @Update("update book_info set name = #{name}, author = #{author}," +
            "content = #{content}, pic = #{pic}, remain = #{remain}, price = #{price} " +
            "where bid = #{bid}")
    void updateBookInfo(BookInfo binfo);

    @Select("select * from book_type")
    List<BookTag> getTagList();
}