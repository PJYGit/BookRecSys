# -*- coding:UTF-8 -*-
# import com.sqoop.buildExport as exp
import sys
sys.path.append('/home/software/files/BookShopBI/BookShopBI/')

import com.sqoop.buildImport as imp
from com.utils.SqoopUtil import SqoopUtil
import datetime

if __name__ == '__main__':
    today = datetime.date.today() + datetime.timedelta(-1)
    dt = today.strftime('%Y-%m-%d')

    # cmds = exp.resolve_conf(dt)
    #
    # for cmd in cmds:
    #     print(cmd)
    #
    #     # 执行导出过程
    #     SqoopUtil.execute_shell(cmd)

    cmds = imp.resolve_conf(dt)

    for cmd in cmds:
        print(cmd)

        # 执行导入过程
        SqoopUtil.execute_shell(cmd)
