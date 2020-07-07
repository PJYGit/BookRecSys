# 6#主页
* /home/top 销量前n书

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
list\<elm\>|书本列表|用户信息列表

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
sales[number]|销量|销量
remain[number]|库存|库存
price[number]|价格|价格

* /home/person 给本用户分析推荐的书

全部同上

* /home/types 图书类别

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
list\<elm\>|分类列表|用户信息列表

其中 elm:
键值|名称|描述
-|-|-
tid|类别id|类别id
name|类别名字|类别名字
cnt|数量|本类别有多少书

请保证返回内容包含{tid:0,name:"全部",cnt:xxxxx}
xxxxx为书总数

* /home/books 指定类别的图书列表

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
tid|类别|类别id

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
list\<elm\>|书本列表|用户信息列表

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
mark|分数|分数
sales|销量|销量
remain|库存|库存

* /home/serach 图书搜索

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
tid|类别|类别id，默认为全部
word|关键字|搜索关键字

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
list\<elm\>|书本列表|用户信息列表

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
mark|分数|分数
sales|销量|销量
remain|库存|库存