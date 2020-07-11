# -*- coding:UTF-8 -*-
import sys

sys.path.append('/home/software/files/BookShopBI/BookShopBI/')

import os


def modify():
    shell = "hadoop fs -get /user/hdfs/itemCF/result/part-r-00000 /home/files/"
    os.system(shell)
    infile = open("/home/files/part-r-00000", "r")
    outfile = open("/home/files/out.csv", "w")
    lines = infile.readlines()
    for line in lines:
        contents = line.split("\t")
        uid = contents[0]
        rec_list = contents[1].replace("[", "").replace("]", "").split(",")
        rank = 1
        for rec in rec_list:
            bid = rec.split(":")[0]
            outline = uid + "," + str(rank) + "," + bid + "\n"
            rank += 1
            outfile.write(outline)
