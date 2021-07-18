# redis 按照

docker 方式安装

docker pull redis

docker 启动

docker run -p 6379:6379 --name redis01 -v /c/files/workspace/java/JavaAdvancedTrain/08cache/ha/conf/redis6379.conf:/etc/redis/redis.conf -v /c/temp/redisdata/redis6379:/data -d redis redis-server /etc/redis/redis.conf --appendonly yes


docker run -p 6380:6380 --name redis02 -v /c/files/workspace/java/JavaAdvancedTrain/08cache/ha/conf/redis6380.conf:/etc/redis/redis.conf -v /c/temp/redisdata/redis6380:/data -d redis redis-server /etc/redis/redis.conf --appendonly yes

# 注意

- 错误： Error response from daemon: invalid mode: /etc/redis/redis.conf 
    - docker 不允许这种目录 c:/  改成  /c/...... 类似的目录 