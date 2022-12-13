
spring cache:
cul http://localhost:8080/user/find?id=1

mybatis cache: 
curl http://localhost:8080/order/find?id=1

hibernate cache: 
curl http://localhost:8080/author/find?id=1
注：需要自己导入hibernate-redis 2.4.0




错误记录：
- Caused by: org.redisson.client.RedisConnectionException: Can't init enough connections amount! Only 0 from 1 were initialized. Server: localhost/127.0.0.1:6379
    