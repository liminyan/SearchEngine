# SearchEngine

#相关文件
+ \Data 下需要包含 ntcir14_test_doc，ntcir14_test_label,ntcir14_test_query 等 提供的数据文件
+ src\main\java 下是源代码
+ res_20.txt 是给定query的搜索结果
+ judeg.txt 是top5.10.20的 指标评价结果

#运行Run.sh 直接运行+评测
+ maven 工程需要 预先配置好maven 然后执行  $mvn package 生成可执行文件
+ $java -jar target/233-1.0-SNAPSHOT-jar-with-dependencies.jar > res_20.txt 执行可执行文件将结果保存到res_20.txt 
+ $python3 judge.py 评价当前的搜索引擎指标
