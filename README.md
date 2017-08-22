#简介
这是uranus的fds，即Flgliht Data Source，是飞行数据源的发生器

#前置条件
- 确保dev-rhel.cscn.net上mysql和activemq状态均为启动

#Profiles
- local
- dev
- release

#生成
1. 使用gradlew build生成
2. 使用gradlew clean清理
3. 使用gradlew jacocoTestReport生成覆盖率报告 

#持续集成
 - 本项目采用了持续集成，在持续集成中的生成方案是：
   - gradlew clean build jacocoTestReport
#合并
1. dev分支为开发主分支，使用了保护策略，只能通过拉取来合并；
2. master分支为release主分支，使用了保护策略，从dev拉取来合并。
3. dev是head
3