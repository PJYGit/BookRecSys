# 3#购物车

以下状态码：-1为请求错误，-10为身份验证错误

---
# /car/getlist 查看我的购物车

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
items[] [list\<elm\>]|车内书本|

其中 elm：
键值|名称|描述
-|-|-
bid|书id|书id
bname|书名|书名
sid|商店id|商店id
sname|商店名|商店名
pic|书图片url|图片
cnt|数量|如果本商品已经无效，请计数量为-1

---
# /car/addbook 添加书

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
bid|书本id|书本id
cnt|个数|个数
sid|商店id|

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

---
# /car/setlist 编辑列表

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
car[] [list\<eml\>]|新购物车|新购物车

其中 elm：
键值|名称|描述
-|-|-
bid|书本id|书本id
cnt|数量|数量

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

---
# /car/submit 结算一部分
从购物车中生成订单，生成后从购物车扣除对应商品。

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
address|地址等|收货信息
buy[] [list\<eml\>]|购买商品列表|购买商品列表

其中 elm：
键值|名称|描述
-|-|-
bid|书本id|书本id
cnt|数量|数量

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败，-2333为商店不统一，-666为空列表，其他情况请参考api4.5
cid|订单id|订单id
