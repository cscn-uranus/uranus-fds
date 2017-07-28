#!/bin/sh
# ##################################################################
# Powered by Ironfo
# ##################################################################
java -jar /home/setupop/sites/uranus-fds/uranus-fds-api-*.jar &
#注意：必须有&让其后台执行，否则没有pid生成
echo $! > /home/setupop/sites/uranus-fds/uranus-fds-api.pid
# 将jar包启动对应的pid写入文件中，为停止时提供pid