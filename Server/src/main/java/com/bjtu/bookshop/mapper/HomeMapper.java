package com.bjtu.bookshop.mapper;

import com.bjtu.bookshop.bean.db.BookInfo;
import com.bjtu.bookshop.bean.db.BookTag;
import com.bjtu.bookshop.bean.middle.HardBook;
import com.bjtu.bookshop.bean.response.HomeResponses;
import com.bjtu.bookshop.bean.response.ShopResponses;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface HomeMapper {

    @Select("select i.bid,i.sid,group_concat(tid) as innerTid,null as tid," +
            "i.name as bname, s.name as sname,author,i.content,i.pic,5 as mark," +
            "sales,remain,price*0.01 from book_info as i " +
            "left join book_tag as t on i.bid=t.bid " +
            "left join store_info as s on i.sid=s.sid " +
            "group by(i.bid) order by -sales limit 20")
    List<HardBook> getList();
}
