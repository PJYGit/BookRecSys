# -*- coding:UTF-8 -*-
import sys

sys.path.append('/home/software/files/BookShopBI/BookShopBI/')

import os
from com.conversion_rate_analysis import buildConversion, buildConversionInput, buildConversionHadoopShell, \
    buildConversionResult

if __name__ == '__main__':
    # 统计时间范围的开始时间，通过命令行参数传入
    start = sys.argv[1]

    # 统计时间范围的结束时间，通过命令行参数传入
    end = sys.argv[2]

    # 解析配置文件
    urls = buildConversion.resolve_conf(0, "")

    # 提取所需数据
    buildConversionInput.extract_data(start, end)

    # 通过MapReduce进行统计
    buildConversionHadoopShell.count_urls(start, end, urls)

    # 对中间结果进行汇总并得到最后结果表
    buildConversionResult.get_result(start, end, "/user/warehouse/conversion_out")

    shell = "hadoop fs -get /user/hive/warehouse/conversion_result/dt=2020-07-07/000000_0_copy_1 /home/files/"
    os.system(shell)

    file = open("/home/files/000000_0_copy_1", "r")
    lines = file.readlines().split("\t")
    file.close()
    out = open("/home/files/conversion_result", "w")
    outline = lines[1] + "," + lines[2]
    out.write(outline)
    out.close()

    shell = "mysql -h 39.106.160.119 -P 3306 -u book_remote -pBUu8Tix56N8do3Yosb -N -e \"use book_shop;load data local infile '/home/files/conversion_result' overwrite into table conversion_result fields terminated by ',';\""
    os.system(shell)