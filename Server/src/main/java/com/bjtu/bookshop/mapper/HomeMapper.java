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
            "sales,remain,price*0.01 as price from book_info as i " +
            "left join book_tag as t on i.bid=t.bid " +
            "left join store_info as s on i.sid=s.sid " +
            "group by(i.bid) order by -sales limit 20")
    List<HardBook> getTopList();

    @Select("select i.bid,i.sid,group_concat(tid) as innerTid,null as tid," +
            "i.name as bname, s.name as sname,author,i.content,i.pic,5 as mark," +
            "sales,remain,price*0.01 as price from book_info as i " +
            "left join book_tag as t on i.bid=t.bid " +
            "left join store_info as s on i.sid=s.sid " +
            "group by(i.bid) order by -i.bid limit 20")
    List<HardBook> getNewList();

    @Select("select v.bid,i.sid,group_concat(tid) as innerTid,null as tid, " +
            "i.name as bname, s.name as sname,author,i.content,i.pic,5 as mark, " +
            "sales,remain,price*0.01 as price from bx_user_view as v " +
            "left join book_info as i on v.bid = i.bid " +
            "left join book_tag as t on i.bid=t.bid " +
            "left join store_info as s on i.sid=s.sid " +
            "where v.uid = #{uid} group by(v.bid) order by v.place")
    List<HardBook> getPerson(int uid);

    @Select("select count(*) from (select * from book_info LIMIT 1000) as t")
    int getTypeSize();

    @Select("select v.bid,i.sid,group_concat(t.tid) as innerTid,null as tid, " +
            "i.name as bname, s.name as sname,author,i.content,i.pic,5 as mark, " +
            "sales,remain,price*0.01 as price from book_tag as v " +
            "left join book_info as i on v.bid = i.bid " +
            "left join book_tag as t on i.bid=t.bid " +
            "left join store_info as s on i.sid=s.sid " +
            "where v.tid = #{tid} group by(v.bid) order by i.sales limit #{page}, 20")
    List<HardBook> getTypeBooks(int tid, int page, boolean real);
    default List<HardBook> getTypeBooks(int tid, int page){
        return getTypeBooks(tid,(page - 1) * 10,true);
    }

    @Select("select i.bid,i.sid,null as innerTid, null as tid, " +
            "i.name as bname, s.name as sname,author,i.content,i.pic,5.0 as mark, " +
            "sales,remain,price*0.01 as price from book_info as i " +
            "left join book_tag as t on i.bid=t.bid " +
            "left join store_info as s on i.sid=s.sid " +
            "where price >= #{lowerP} and price <= #{upperP} " +
            "and i.name like concat('%',#{word},'%') ${whereAddition} " +
            "group by(i.bid) order by ${orderByPart} limit 100")
    List<HardBook> getSearchBooks(String word, String whereAddition, String orderByPart, int lowerP, int upperP);

    @Select("select 1.0*val_in/val_all from conversion_result limit 1")
    double getCvt();
}
