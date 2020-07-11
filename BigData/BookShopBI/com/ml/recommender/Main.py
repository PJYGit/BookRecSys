# -*- coding:UTF-8 -*-
import sys

sys.path.append('/home/software/files/BookShopBI/BookShopBI/')

import os

from com.utils.ModifyOutput import modifyOutput
from com.utils.py_env import HADOOP_PATH, PROJECT_LIB_DIR

if __name__ == '__main__':
    # 远程连接数据库读取preference信息，存入本地
    shell = "mysql -h 39.106.160.119 -P 3306 -u book_remote -pZBw046vGX7D0eB3o --execute=\"select * from bx_book_ratings;\" book_shop > /home/files/bx_book_ratings.csv"
    os.system(shell)
    # 删除第一行数据库表键值信息
    shell = "sed -i '1d' /home/files/bx_book_ratings.csv"
    os.system(shell)
    # 逗号分隔
    shell = "cat /home/files/bx_book_ratings.csv | sed 's/\t/,/g' > /home/files/bx_ratings.csv"
    os.system(shell)

    # 删除原有文件，讲处理好的文件放到输入目录
    shell = "hadoop fs -rm /user/hdfs/itemCF/input/*"
    os.system(shell)
    shell = "hadoop fs -put /home/files/bx_ratings.csv /user/hdfs/itemCF/input/"
    os.system(shell)
    shell = "hadoop fs -ls /user/hdfs/itemCF/input/"
    os.system(shell)

    # 执行推荐算法的jar包
    shell = HADOOP_PATH + "hadoop jar " + PROJECT_LIB_DIR + "usercluster.jar com.recommender.cf_hadoop.ItemCFHadoop"
    os.system(shell)

    # 处理结果，格式化
    modifyOutput()

    # 将结果写入远程数据库
    shell = "mysql -h 39.106.160.119 -P 3306 -u book_remote -pZBw046vGX7D0eB3o -N -e \"use book_shop;load data local infile '/home/files/out.csv' into table bx_user_view fields terminated by ',';\""
    os.system(shell)
