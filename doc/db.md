# 1.用户表

## user_reg
* uid  [主，自增]
* urn
* pwd
* salt

## user_login
* uid  [主]
* token

## user_info
* uid  [主]
* urn
* nickname
* regtime
* head
* viprate
* baned
* money

## user_address
* uid  [主]
* plid  [主]
* address
* default

# 2.书本表

## book_info
* bid  [主，自增]
* name
* auther
* sid  对应商店id
* content
* pic
* sales  销量
* remain  库存余量

## book_type 【手录数据的静态表】
* tid  [主]
* name

## book_comment
* cid  [主，自增]
* bid  评价书本的bid
* uid  评价者uid
* mark 评分
* content