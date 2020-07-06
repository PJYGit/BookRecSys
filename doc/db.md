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