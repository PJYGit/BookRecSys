package com.bjtu.bookshop.mapper;

import java.util.List;

import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.BookTag;

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

    @Update("update book_info set  where bid = #{bid}")
    void updateBookInfo(BookInfo info);

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
}
