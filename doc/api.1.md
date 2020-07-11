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

返回实例：

```json
{
    "item": {
        "uid": 7,
        "token": "a04baa4f1afd48345ee5a500933d82fe"
    },
    "state": 0
}
```



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

返回实例：

```json

```



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

返回实例：

```json
{
    "item": {
        "uid": 12,
        "token": "2c962b8c54006872afc9c123d253787a"
    },
    "state": 0
}
```



## 1.4/user/phone 发送手机验证码[不写先]

POST

request:

键值|名称|描述
-|-|-
urn|手机号|手机号字串

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

返回实例：

```json

```



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
address[] [list\<loc\>]|地址列表|地址列表
managed[] [list\<elm\>]|管理的店铺|管理的店铺列表

其中 loc：
键值|名称|描述
-|-|-
title|地址名|地址名
content|地址内容|地址内容
name|收货人姓名|收货人姓名
phone|手机号|手机号
selected|是否默认|是1 不是0

其中 elm：
键值|名称|描述
-|-|-
sid|商店id|商店id
sname|商店名|商店名
boss|是不是老板|是1不是0

返回实例：

```json
{
    "item": {
        "uid": 7,
        "urn": "18876002015",
        "nickname": "李高丞",
        "regtime": 1594023395,
        "head": "test",
        "viprate": 1.0,
        "baned": 0,
        "money": "0",
        "role": 2
    },
    "state": 0
}
```



## 1.6/user/setinfo 修改本用户基本信息

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
head|头像|头像
nickname|昵称|昵称
address[] [list\<elm\>]|地址|地址，elm见实例

数组传递方法：
``` js
var fd = new FormData();

fd.append('uid',18);
fd.append('token','a5b64212bc603e50cd2b4cbe8a1fb5d6');
fd.append('address',JSON.stringify({'title':'','content':'','name':'','phone':'','selected':1}));
fd.append('address',JSON.stringify({'title':'','content':'','name':'','phone':'','selected':1}));
fd.append('address',JSON.stringify({'title':'','content':'','name':'','phone':'','selected':1}));

```

可选项为上述所有可修改项

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

返回实例：

```json
{
    "state": 0
}
```



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
pagesize|有多少页|页数
list\<elm\>|用户列表|用户信息列表

其中 elm:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
urn|手机号|手机号
token|验证串|和 uid 一起做身份验证
nickname|昵称|昵称
regtime|注册时间|注册时间 unix 秒时间戳
vipRate|vip 价格系数|1 不打折 0.8 八折 以此类推
baned|封禁|0 正常 1 封禁
role|身份|0 普通用户，1 系统管理，2 超级管理
money|余额|余额

返回实例：

```json
{
    "list": [
        {
            "uid": 3,
            "urn": "test",
            "nickname": null,
            "regtime": 1594019916000,
            "head": null,
            "viprate": 1.0,
            "baned": 0,
            "money": "0",
            "role": 0
        },
        {
            "uid": 7,
            "urn": "18876002015",
            "nickname": "李高丞",
            "regtime": 1594023395,
            "head": "test",
            "viprate": 1.0,
            "baned": 0,
            "money": "100",
            "role": 2
        },
        {
            "uid": 10,
            "urn": "18876002000",
            "nickname": "test1",
            "regtime": 1594197562,
            "head": null,
            "viprate": 1.0,
            "baned": 0,
            "money": "0",
            "role": 1
        },
        {
            "uid": 11,
            "urn": "18876002015",
            "nickname": null,
            "regtime": 1594261591,
            "head": null,
            "viprate": 1.0,
            "baned": 0,
            "money": "0",
            "role": 0
        },
        {
            "uid": 12,
            "urn": "18876002011",
            "nickname": null,
            "regtime": 1594261699,
            "head": null,
            "viprate": 1.0,
            "baned": 0,
            "money": "0",
            "role": 0
        }
    ],
    "state": 0
}
```



## 1.s.2/user/manage/search 搜索用户

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
phone|手机号|手机号字串

```json
{
    "uid": 7,
    "token": "a04baa4f1afd48345ee5a500933d82fe",
    "phone": "188"
}
```

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

返回实例：

```json
{
    "list": [
        {
            "uid": 7,
            "urn": "18876002015",
            "nickname": "李高丞",
            "regtime": 1594023395,
            "head": "test",
            "viprate": 1.0,
            "baned": 0,
            "money": "100",
            "role": 2
        },
        {
            "uid": 10,
            "urn": "18876002000",
            "nickname": "test1",
            "regtime": 1594197562,
            "head": null,
            "viprate": 1.0,
            "baned": 0,
            "money": "0",
            "role": 1
        },
        {
            "uid": 11,
            "urn": "18876002015",
            "nickname": null,
            "regtime": 1594261591,
            "head": null,
            "viprate": 1.0,
            "baned": 0,
            "money": "0",
            "role": 0
        },
        {
            "uid": 12,
            "urn": "18876002011",
            "nickname": null,
            "regtime": 1594261699,
            "head": null,
            "viprate": 1.0,
            "baned": 0,
            "money": "0",
            "role": 0
        }
    ],
    "state": 0
}
```



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

返回实例：

```json
{
    "item": {
        "uid": 7,
        "urn": "18876002015",
        "nickname": "李高丞",
        "regtime": 1594023395,
        "head": "test",
        "viprate": 1.0,
        "baned": 0,
        "money": "100",
        "role": 2
    },
    "state": 0
}
```



## 1.s.4/user/manage/setinfo 修改某用户信息

POST

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
target [number]|目标 uid|目标用户 uid
data[object]||
```json
{
    "uid": 7,
    "token": "a04baa4f1afd48345ee5a500933d82fe",
    "target": 7,
    "data": {
        "money": 200
    }
}
```

其余可选项见上

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败

特别注意的是：

    1.只有2可以改role，且不能改成2或从2改成别的
    2.封号只能1或2来

返回实例：

```json
{
    "state": 0
}
```



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

返回实例：

```json
{
    "item": 15,
    "state": 0
}
```

