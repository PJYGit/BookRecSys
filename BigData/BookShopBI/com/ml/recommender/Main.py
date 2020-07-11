# -*- coding:UTF-8 -*-
import sys

sys.path.append('/home/software/files/BookShopBI/BookShopBI/')

import os

import com.utils.ModifyOutput

if __name__ == '__main__':
    # 远程连接数据库读取preference信息，存入本地
    shell = "mysql -h 39.106.160.119 -P 3306 -u book_remote -pZBw046vGX7D0eB3o --execute=\"select * from user_info;\" book_shop > /home/files/user_info"
    os.system(shell)
    # 删除第一行数据库表键值信息
    shell = "sed -i '1d' /home/files/user_info"
