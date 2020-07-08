import sys
sys.path.append('/home/software/files/BookShopBI/BookShopBI/')

from com.utils.HiveUtil import HiveUtil


def prepare_normaliz(start_time, end_time):
    # 提取数据维度
    # hql_1 = "insert overwrite table user_dimension select t1.user_id,t1.avg,t2.ordercount,t3.sessioncount from " \
    #         "(select user_id,avg(payment) avg from orders" + " group by user_id) t1 " \
    #         "join (select user_id,count(order_id) ordercount from orders" + " group by user_id) t2 on t1.user_id=t2.user_id " \
    #         "join (select userId,count(sessionId) sessioncount from clickstream_log" + " group by userId) t3 on t1.user_id=t3.userId";
    # HiveUtil.execute_shell(hql_1)

    # 将user_dimension数据导入到cluster_input中，去掉CustomerId
    # hql_2 = "insert overwrite table cluster_input select subtotal,orderscount,sessioncount from user_dimension";
    # HiveUtil.execute_shell(hql_2)
    #
    # # 数据归一化处理
    # hql_3 = "insert overwrite table cluster_input " \
    #         "select (subtotal-avg_subtotal)/std_subtotal,(orderscount-avg_orderscount)/std_orderscount,(sessioncount-avg_sessioncount)/std_sessioncount from cluster_input " \
    #         "join (select std(subtotal) std_subtotal,std(orderscount) std_orderscount,std(sessioncount) std_sessioncount from cluster_input) t1 on 1=1 " \
    #         "join (select avg(subtotal) avg_subtotal,avg(orderscount) avg_orderscount,avg(sessioncount) avg_sessioncount from cluster_input) t2 on 1=1";
    # HiveUtil.execute_shell(hql_3)
    #
    # #计算相关系数矩阵
    hql_4 = "select (avg(subtotal*subtotal)-avg(subtotal)*avg(subtotal))/(std(subtotal)*std(subtotal))," \
          "(avg(subtotal*orderscount)-avg(subtotal)*avg(orderscount))/(std(subtotal)*std(orderscount))," \
          "(avg(subtotal*sessioncount)-avg(subtotal)*avg(sessioncount))/(std(subtotal)*std(sessioncount))," \
          "(avg(orderscount*subtotal)-avg(orderscount)*avg(subtotal))/(std(orderscount)*std(subtotal))," \
          "(avg(orderscount*orderscount)-avg(orderscount)*avg(orderscount))/(std(orderscount)*std(orderscount))," \
          "(avg(orderscount*sessioncount)-avg(orderscount)*avg(sessioncount))/(std(orderscount)*std(sessioncount))," \
          "(avg(sessioncount*subtotal)-avg(sessioncount)*avg(subtotal))/(std(sessioncount)*std(subtotal))," \
          "(avg(sessioncount*orderscount)-avg(sessioncount)*avg(orderscount))/(std(sessioncount)*std(orderscount))," \
          "(avg(sessioncount*sessioncount)-avg(sessioncount)*avg(sessioncount))/(std(sessioncount)*std(sessioncount)) " \
          "from cluster_input";
    HiveUtil.execute_shell(hql_4)