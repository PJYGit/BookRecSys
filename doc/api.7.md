# 7#书籍
# /book/info 取单书信息

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
sid [number]|商家id|商家id
tid[]|分类id|分类id数组
bname|书本名|书本名
sname|商家名|商家名
author|作者|作者
content|书简介|书简介
pic|书图片|书图片
mark[number]|分数|分数
sales|销量|销量
remain|库存|库存
price[number]|价格|价格
comment[] [list\<elm\>]|评价表|评价表

其中elm：
uid|用户id|用户id
uname|用户名|用户名
mark|评分|评分
content|评论内容|评论内容
time|评论时间|评论时间