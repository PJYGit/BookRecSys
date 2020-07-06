# 1#用户
## 1.1 /user/login 用户登录

request:
键值|名称|描述
-|-|-
urn|用户名|手机号字串
pw|密码|密码哈希

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证

注：1.哈希你随意

## 1.2/user/logout 用户登出

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败

## 1.3/user/register 用户注册

request:
键值|名称|描述
-|-|-
urn|手机号|手机号字串
uname|用户名|可重用户名
code|验证码|验证码，你先写成 = ("000000".equal(code))

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证

## 1.4/user/phone 发送手机验证码[不写先]

request:
键值|名称|描述
-|-|-
urn|手机号|手机号字串

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败

## 1.5/user/getinfo 取本用户基本信息

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
uid [number]|用户id|复读就完事了
urn |用户账号|手机号字串
nickname|昵称|注册时指定
role|身份标志|0普通用户，1系统管理，2超级管理
head|头像url|头像url
regtime|注册时间|注册时间unix秒时间戳
vipRate [number]|vip价格系数|1不打折 0.8八折 以此类推
money|余额|余额

## 1.6/user/setinfo 修改本用户基本信息

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
可选项为上述所有可修改项

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败

## 1.s.1/user/manage/list 取用户列表

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
page [number]|页码|页码，每页20个，默认第一页

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
list\<elm\>|用户列表|用户信息列表

其中 elm:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
nickname|昵称|昵称
regtime|注册时间|注册时间unix秒时间戳
vipRate|vip价格系数|1不打折 0.8八折 以此类推
baned|封禁|0正常1封禁
role|身份|0普通用户，1系统管理，2超级管理
money|余额|余额

## 1.s.2/user/manage/search 搜索用户

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
phone|手机号|手机号字串

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
list\<elm\>|用户列表|用户信息列表

其中 elm:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
nickname|昵称|昵称
regtime|注册时间|注册时间unix秒时间戳
vipRate|vip价格系数|1不打折 0.8八折 以此类推
baned|封禁|0正常1封禁
role|身份|0普通用户，1系统管理，2超级管理
money|余额|余额

## 1.s.3/user/manage/getinfo 取某用户信息

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
target [number]|目标uid|目标用户uid

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败
data [object]|用户信息|用户信息对象

其中 data:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
nickname|昵称|昵称
regtime|注册时间|注册时间unix秒时间戳
vipRate|vip价格系数|1不打折 0.8八折 以此类推
baned|封禁|0正常1封禁
role|身份|0普通用户，1系统管理，2超级管理
money|余额|余额

## 1.s.4/user/manage/setinfo 修改某用户信息

request:
键值|名称|描述
-|-|-
uid [number]|用户id|系统内唯一标志符，用于后续操作
token|验证串|和uid一起做身份验证
target [number]|目标uid|目标用户uid
其余可选项见上

response:
键值|名称|描述
-|-|-
state [number]|返回码|0为正常，-1为失败

特别注意的是：

    1.只有2可以改role，且不能改成2或从2改成别的
    2.封号只能1或2来
