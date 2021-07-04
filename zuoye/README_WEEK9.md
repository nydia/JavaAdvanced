# Java进阶训练营实战 作业

## 第八周作业


### 作业答案目录
1. 作业3
2. 作业7

### 前置



### 作业详细

#### 第3题  改造自定义 RPC 的程序

(1) 尝试将服务端写死查找接口实现类变成泛型和反射；

RpcfxInvoker#invoke():

原来的写死接口：
~~~java
this.applicationContext.getBean(serviceClass);
~~~

动态代理：
~~~java
Object service = resolver.resolve(serviceClass);
~~~

(2) 尝试将客户端动态代理改成 AOP，添加异常处理；
(3) 尝试使用 Netty+HTTP 作为 client 端传输方式。

原有：
~~~java

~~~

改造之后：
~~~

~~~

#### 第7题  结合 dubbo+hmily，实现一个 TCC 外汇交易处理
