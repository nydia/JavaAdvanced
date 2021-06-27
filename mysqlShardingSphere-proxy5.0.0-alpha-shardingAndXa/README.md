##  使用中间件 Shardingsphere-proxy 对mysql水平分库分表，并且处理事务

## 前置注意

1. 安装启动shardingsphere-proxy的启动端口 3308
2. 插入和更新数据的时候：
    - t_order的order_id和user_id不能为空
    - t_order的order_item_id和user_id不能为空
    - user_id用于库路由,order_id用于表路由，具体参见配置

## proxy 配置

## 使用

