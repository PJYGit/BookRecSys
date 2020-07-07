# DB-Design

## 1.用户表

### user_reg

- uid [主，自增]
- urn
- pwd
- salt

### user_login

- uid [主]
- token

### user_info

- uid [主]
- urn
- nickname
- regtime
- head
- viprate
- baned
- money
- role # 添加角色类型

### user_address

- uid [主]
- plid [主]
- address
- default

## 2.书本表

### book_info

- bid [主，自增]
- name
- author
- sid 对应商店 id
- tid 对应类型 id
- content
- pic
- sales 销量
- remain 库存余量

### book_type 【手录数据的静态表】

- tid [主]
- name

### book_comment

- cid [主，自增]
- bid 评价书本的 bid
- uid 评价者 uid
- mark 评分
- content

### book_tag
- bid [主]
- tid [主]

## 3. 商店表

### store_info

- sid
- name
- boss
- content
- code
- head
- mark

### store_manage

- uid
- name
- sid