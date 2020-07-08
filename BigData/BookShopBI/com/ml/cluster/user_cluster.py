import sys

sys.path.append('/home/software/files/BookShopBI/BookShopBI/')

import os
from com.utils.HiveUtil import HiveUtil
from com.utils.py_env import HADOOP_PATH, PROJECT_LIB_DIR, PROJECT_TMP_DIR


def cluster_output():
    clusterOutputPath = "/user/hadoop/clusterOutput"
    t1 = "100"
    t2 = "10"
    convergenceDelta = "0.5"
    maxIterations = "10"

    # 执行聚类
    shell = HADOOP_PATH + "hadoop jar " + PROJECT_LIB_DIR + "usercluster.jar com.clustering.KMeansMahout " + clusterOutputPath \
            + " /user/hive/warehouse/cluster_input " + t1 + " " + t2 + " " + convergenceDelta + " " + maxIterations
    os.system(shell)

    # 解析聚类结果文件并输出至本地
    shell = HADOOP_PATH + "hadoop jar " + PROJECT_LIB_DIR + "usercluster.jar com.clustering.ClusterOutput " + clusterOutputPath \
            + " " + PROJECT_TMP_DIR + "result"
    os.system(shell)

    # 删除聚类结果文件中存留的空行
    shell = "sed '/^$/d' " + PROJECT_TMP_DIR + "result.txt > " + PROJECT_TMP_DIR + "temp.out"
    os.system(shell)
    shell = "rm -rf " + PROJECT_TMP_DIR + "result.txt"
    os.system(shell)
    shell = "mv " + PROJECT_TMP_DIR + "temp.out " + PROJECT_TMP_DIR + "result.txt"
    os.system(shell)

    # 将本地的结果文件加载到Hive的cluster_result表中
    hql = "load data local inpath '" + PROJECT_TMP_DIR + "result.txt' overwrite into table cluster_result"
    HiveUtil.execute_shell(hql)


def get_finalresult():
    hql = "insert overwrite table final_result select t2.customerid,t1.* from " \
          "(select clusterid,subtotal,orderscount,sessioncount from cluster_result group by clusterid,subtotal,orderscount,sessioncount) t1 " \
          "join (select customerid,(subtotal-avg_subtotal)/std_subtotal subtotal,(orderscount-avg_orderscount)/std_orderscount orderscount," \
          "(sessioncount-avg_sessioncount)/std_sessioncount sessioncount from user_dimension " \
          "join (select std(subtotal) std_subtotal,std(orderscount) std_orderscount,std(sessioncount) std_sessioncount from user_dimension) t1 on 1=1 " \
          "join (select avg(subtotal) avg_subtotal,avg(orderscount) avg_orderscount,avg(sessioncount) avg_sessioncount from user_dimension) t2 on 1=1) t2 " \
          "on t1.subtotal=t2.subtotal and t1.orderscount=t2.orderscount and t1.sessioncount=t2.sessioncount"
    HiveUtil.execute_shell(hql)
