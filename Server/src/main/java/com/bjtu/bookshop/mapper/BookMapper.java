package com.bjtu.bookshop.mapper;

import java.util.List;

import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.BookTag;

import com.bjtu.bookshop.bean.response.ShopResponses;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BookMapper {

    @Insert("insert into book_info(name, author, sid, content, pic, sales, remain, price) values(#{name}, #{author}, #{sid}, #{content}, #{pic}, #{sales}, #{remain}, #{price})")
    void insertNewBookIntoBookInfo(BookInfo info);

    @Delete("delete from book_info where bid = #{bid}")
    void deleteBookWithBID(int bid);

    @Update("update book_info set sales = sales + #{cnt}, remain = remain - #{cnt} where bid = #{bid}")
    void updateBookSalesAndRemain(int bid, int cnt);

    @Select("select * from book_info where bid = #{bid}")
    BookInfo getBookInfoWithBID(int bid);

    @Select("select * from book_info where sid = #{sid} and name = #{name}")
    BookInfo getBookInfoWithNameAndSID(int sid, String name);

    @Select("select * from book_info where sid = #{sid}")
    List<BookInfo> getBookInfoWithSID(int sid);

    @Select("select * from book_tag where bid = #{bid}")
    List<BookTag> getBookTagWithBID(int bid);

    @Insert("insert into book_tag(bid, tid) values(#{bid}, #{tid})")
    void insertBookTag(int bid, int tid);

    @Delete("delete from book_tag where bid = #{bid}")
    void deleteBookAllTagWithBID(int bid);

    ///////////////////////////////////////////////////

    @Select("select i.bid,i.sid,group_concat(tid) as innerTid,null as tid," +
            "i.name as bname, s.name as sname,author,i.content,i.pic,5 as mark," +
            "sales,remain,price*0.01 as price from book_info as i " +
            "left join book_tag as t on i.bid=t.bid " +
            "left join store_info as s on i.sid=s.sid " +
            "where i.sid = #{sid} group by(i.bid)")
    List<ShopResponses.BookListResponse.book> getBookListBySid(int sid);

    @Select("select i.bid,i.sid,group_concat(tid) as innerTid,null as tid," +
            "i.name as bname, s.name as sname,author,i.content,i.pic,5 as mark," +
            "sales,remain,price*0.01 as price from book_info as i " +
            "left join book_tag as t on i.bid=t.bid " +
            "left join store_info as s on i.sid=s.sid " +
            "where i.bid = #{bid} group by(i.bid)")
    ShopResponses.BookListResponse.book getBookByBid(int bid);
}
