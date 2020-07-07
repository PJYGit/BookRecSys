    API 0.1 快速迭代中，随时可能在不通知全员的情况下改动（权利在gakki手上），请实时关注

---
# 1#用户
* /user/login 用户登录
* /user/logout 用户登出
* /user/register 用户注册
* /user/phone 发送手机验证码[不写先]
* /user/getinfo 取本用户基本信息
* /user/setinfo 修改本用户基本信息

1.sub#用户管理
* /user/manage/list 取用户列表
* /user/manage/search 搜索用户
* /user/manage/getinfo 取某用户信息
* /user/manage/setinfo 修改某用户信息
* /user/manage/adduser 创建新用户

# 2#店铺
* /shop/getinfo 取某商店信息
* /shop/booklist 取某商店书本列表
* /shop/getbookinfo 取书信息

2.sub#店铺管理
* /shop/manage/list 取店铺列表
* /shop/manage/getinfo 取某用户信息
* /shop/manage/setinfo 修改某用户信息
* /shop/manage/addbook 添加书
* /shop/manage/delbook 删除书
* /shop/manage/setbookinfo 设置书信息

# 3#购物车
* /car/getlist 查看我的购物车
* /car/addbook 添加书
* /car/setlist 编辑列表
* /car/submit 结算一部分

# 4#订单
* /order/getlist 查看本人所有订单
* /order/getinfo 单个订单信息
* /order/operate 支付/取消/确认收货
* /order/comment 评价
* /order/create 单书直接创建订单

4.sub#订单管理
* /shop/manage/getlist 取商铺订单
* /shop/manage/operate 确认发货/取消
* /shop/manage/setinfo 修改价格/修改产品列表

# 5#图片上传
* /pic/upload 上传图片

# 6#主页
* /home/top 销量前n书
* /home/person 给本用户分析推荐的书
* /home/types 图书类别
* /home/books 指定类别的图书列表
* /home/serach 图书搜索

//大概大纲还没写完？
//第一波写完了，马上开始捋业务逻辑补充

改动：
+ root创建用户接口  添加1.s.5
+ 店铺搜索（用户）
+ 店铺头图设置/传递
+ 单书详细信息（卧槽我居然没写这个？）
+ 个人到管理店铺的桥梁