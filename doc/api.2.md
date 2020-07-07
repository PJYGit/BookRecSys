# 2#店铺
* /shop/getinfo 取某商店信息

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
sid|商店id|商店id

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
name|商店名|
boss [user]|店主|店主
manager[] [list\<user\>]|管理员|管理员
content|商店简介|
code|商店状态|1审核中2正常3封禁
head|商店头像url
mark|商店书本均分|

其中 user：
键值|名称|描述
-|-|-
uid [number]|用户id|用户id
name|昵称|昵称

---
* /shop/booklist 取某商店书本列表

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
sid|商店id|商店id

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
books[] [list\<elm\>]|书本列表|书本列表

其中 elm:
键值|名称|描述
-|-|-
bid [number]|书本id|书本id
sid [number]|商家id|商家id
tid[]|分类id|分类id数组
bname|书本名|书本名
sname|商家名|商家名
auther|作者|作者
content|书简介|书简介
pic|书图片|书图片
mark[number]|分数|分数
sales|销量|销量
remain|库存|库存
price[number]|价格|价格

---
* /shop/getbookinfo 取书信息

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
sid|商店id|商店id
bid|书本id|书本id

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
tid[]|分类id|分类id数组
bname|书本名|书本名
sname|商家名|商家名
auther|作者|作者
content|书简介|书简介
pic|书图片|书图片
mark[number]|分数|分数
sales|销量|销量
remain|库存|库存
price[number]|价格|价格

---
2.sub#店铺管理

---
* /shop/manage/list 取店铺列表

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
page [number]|页码|页码，每页 20 个，默认第一页

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
shops[] [list\<elm\>]|商店列表|商店列表

其中 elm：
键值|名称|描述
-|-|-
sid|商店id|商店id
state [number]|返回码|0 为正常，-1 为失败
name|商店名|
boss [user]|店主|店主（user定义见前文）
manager[] [list\<user\>]|管理员|管理员
content|商店简介|
code|商店状态|1审核中2正常3封禁
head|商店头像url|
mark|商店书本均分|

---
* /shop/manage/getinfo 取某用户/某店信息

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
uid|店主id|店主id
sid|商店id|商店id

(注：保证请求中uid和sid至少有一个)

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
uid|店主id|店主id
sid|商店id|商店id
name|商店名|
boss [user]|店主|店主
manager[] [list\<user\>]|管理员|管理员
content|商店简介|
code|商店状态|1审核中2正常3封禁
head|商店头像url
mark|商店书本均分|

* /shop/manage/setinfo 修改某用户/某店信息

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
uid|店主id|店主id
sid|商店id|商店id
manager[] [list\<user\>]|管理员|管理员
content|商店简介|
code|商店状态|1审核中2正常3封禁
head|商店头像url

(注：保证请求中uid和sid至少有一个)

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

---
## /shop/manage/addbook 添加书

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
tid[]|分类id|分类id数组
bname|书本名|书本名
auther|作者|作者
content|书简介|书简介
pic|书图片|书图片
remain|库存|库存
price[number]|价格|价格

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

## /shop/manage/delbook 删除书

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
bid|书id|书id

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

## /shop/manage/setbookinfo 设置书信息

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
bid|指定书id|指定书id
tid[]|分类id|分类id数组
bname|书本名|书本名
auther|作者|作者
content|书简介|书简介
pic|书图片|书图片
remain|库存|库存
price[number]|价格|价格

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败