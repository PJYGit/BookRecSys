# 5#图片上传
# /pic/upload 上传图片

request:

键值|名称|描述
-|-|-
uid [number]|用户 id|系统内唯一标志符，用于后续操作
token|验证串|和 uid 一起做身份验证
pic [file]|图片|图片

response:

键值|名称|描述
-|-|-
state [number]|返回码|0 为正常，-1 为失败
url|图片地址|图片地址