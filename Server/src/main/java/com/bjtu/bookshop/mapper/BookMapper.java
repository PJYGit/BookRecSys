package com.bjtu.bookshop.mapper;

import java.util.List;

import com.bjtu.bookshop.entity.BookInfo;
import com.bjtu.bookshop.entity.BookTag;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BookMapper {

    @Select("select * from book_info where bid = #{bid}")
    BookInfo getBookInfoWithBID(int bid);

    @Select("select * from book_info where sid = #{sid}")
    List<BookInfo> getBookInfoWithSID(int sid);

    @Select("select * from book_tag where bid = #{bid}")
    List<BookTag> getBookTagWithBID(int bid);

    @Delete("delect from book_info where bid = #{bid}")
    void deleteBookWithBID(int bid);
}
