import sys
sys.path.append('/home/software/files/BookShopBI/BookShopBI/')

from com.utils import HiveUtil


def extract_data(start, end):
    hql = "insert into table conversion_input partition (dt='" + start + "-" + end + "') " \
           "select url,userId,sessionId,csvp from clickstream_log"

    HiveUtil.HiveUtil.execute_shell(hql)
