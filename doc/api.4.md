# 4#订单

以下状态码：-1为请求错误，-10为身份验证错误

价格计算：固定运费22
$$
本类总价=单价 \times 个数 \times VIP比例
$$

$$
订单总价=\sum{本类总价}+固定运费（暂定22，发顺丰）
$$



---
# /order/getlist 查看本人所有订单

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
type [number]|订单类型筛选|0待支付 1待发货 2待确认收货 3待评价 4已完成 -1已取消

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
items[] [list\<elm\>]|订单列表|

其中 elm：
键值|名称|描述
-|-|-
cid [number]|订单id|订单id
type|订单类型|0待支付 1待发货 2待确认收货 3待评价 4已完成 -1已取消
sid [number]|商家id|商家id
sname|商家名|商家名
pic|商品图片|有多个的话随便取一个
items[] [list\<smp\>]|商品列表|见下文

其中smp： 
键值|名称|描述
-|-|-
name|商品名称|商品名称
cnt [number]|个数|个数

---
# /order/getinfo 单个订单信息

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
cid [number]|订单id|订单id

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
cid [number]|订单id|订单id
type [number]|订单类型|0待支付 1待发货 2待确认收货 3待评价 4已完成 -1已取消
sid [number]|商家id|商家id
sname|商家名|商家名
items[] [list\<cfd\>]|商品列表|见下文

其中cfd： 
键值|名称|描述
-|-|-
bid [number]|书本id|书本id
name|书本名|书本名称
cnt [number]|个数|个数
pic|图片url|图片
money [number %.2f]|本类总价|本类总价

---
# /order/operate 支付/取消/确认收货

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
cid [number]|订单id|订单id
opcode|操作类型|1支付 2取消 3确认收货

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

---
# /order/comment 评价

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
cid [number]|订单id|订单id
mark [number]|打分|int 0-5
comment|评价内容|内容

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

---
# /order/create 单书直接创建订单

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
bid [number]|书本id|书本id
cnt [number]|个数|个数

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
cid [number]|订单号|订单号

---
4.sub#订单管理
# /shop/manage/getlist 取商铺订单

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
type [number]|订单类型筛选|0待支付 1待发货 2待确认收货 3待评价 4已完成 -1已取消

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
items[] [list\<elm\>]|订单列表|

其中 elm：
键值|名称|描述
-|-|-
cid [number]|订单id|订单id
type|订单类型|0待支付 1代发货 2待确认收货 3待评价 4已完成 -1已取消
items[] [list\<mmp\>]|商品列表|见下文

其中mmp： 
键值|名称|描述
-|-|-
bid [number]|书本id|书本id
name|书本名|书本名称
cnt [number]|个数|个数
pic|书图url|书图
money [number %.2f]|本类总价|本类总价

---
# /shop/manage/operate 确认发货/取消

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
cid [number]|订单id|订单id
opcode|操作类型|cancel取消 sent确认发货

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

---
# /shop/manage/setinfo 修改价格/修改产品列表

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
cid [number]|订单id|订单id
list[] [list\<elm\>]|订单内容|订单内容

其中 elm：
键值|名称|描述
-|-|-
bid [number]|书本id|书本id
cnt [number]|个数|个数
money [number %.2f]|本类总价|本类总价

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败