# 1#用户

## 1.1 /user/login 用户登录

POST

request:

键值|名称|描述
-|-|-
urn|用户名|手机号字串
pw|密码|密码哈希

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证

注：1.哈希你随意

## 1.2/user/logout 用户登出

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

## 1.3/user/register 用户注册

POST

request:

键值|名称|描述
-|-|-
urn|手机号|手机号字串
uname|用户名|可重用户名
code|验证码|验证码，你先写成 = ("000000".equal(code))
psw|密码|

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证

## 1.4/user/phone 发送手机验证码[不写先]

GET

request:

键值|名称|描述
-|-|-
urn|手机号|手机号字串

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

## 1.5/user/getinfo 取本用户基本信息

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
uid [number]|用户 id|复读就完事了
urn |用户账号|手机号字串
nickname|昵称|注册时指定
role|身份标志|0 普通用户，1 系统管理，2 超级管理
head|头像 url|头像 url
regtime|注册时间|注册时间 unix 秒时间戳
vipRate [number]|vip 价格系数|1 不打折 0.8 八折 以此类推
money|余额|余额
managed[] [list\<elm\>]|管理的店铺|管理的店铺列表

其中 elm：
键值|名称|描述
-|-|-
sid|商店id|商店id
sname|商店名|商店名
boss|是不是老板|是1不是0

## 1.6/user/setinfo 修改本用户基本信息

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
可选项为上述所有可修改项

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

## 1.s.1/user/manage/list 取用户列表

POST

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
list\<elm\>|用户列表|用户信息列表

其中 elm:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
nickname|昵称|昵称
regtime|注册时间|注册时间 unix 秒时间戳
vipRate|vip 价格系数|1 不打折 0.8 八折 以此类推
baned|封禁|0 正常 1 封禁
role|身份|0 普通用户，1 系统管理，2 超级管理
money|余额|余额

## 1.s.2/user/manage/search 搜索用户

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
phone|手机号|手机号字串

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
list\<elm\>|用户列表|用户信息列表

其中 elm:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
nickname|昵称|昵称
regtime|注册时间|注册时间 unix 秒时间戳
vipRate|vip 价格系数|1 不打折 0.8 八折 以此类推
baned|封禁|0 正常 1 封禁
role|身份|0 普通用户，1 系统管理，2 超级管理
money|余额|余额

## 1.s.3/user/manage/getinfo 取某用户信息

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
target [number]|目标 uid|目标用户 uid

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
data [object]|用户信息|用户信息对象

其中 data:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
nickname|昵称|昵称
regtime|注册时间|注册时间 unix 秒时间戳
vipRate|vip 价格系数|1 不打折 0.8 八折 以此类推
baned|封禁|0 正常 1 封禁
role|身份|0 普通用户，1 系统管理，2 超级管理
money|余额|余额

## 1.s.4/user/manage/setinfo 修改某用户信息

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
target [number]|目标 uid|目标用户 uid
其余可选项见上

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

特别注意的是：

    1.只有2可以改role，且不能改成2或从2改成别的
    2.封号只能1或2来

## 1.s.5/user/manage/adduser 添加新用户

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
urn|手机号|
password|密码|密码哈希（你1做没做哈希啊，做了就这个也做）（就MD5了）
nickname|昵称|昵称
vipRate|vip 价格系数|1 不打折 0.8 八折 以此类推
role|身份|0 普通用户，1 系统管理
baned|是否被Block|0 没有，1 block
money|余额|余额

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
uid [number]|用户 id|系统内唯一标志符，用于后续操作
