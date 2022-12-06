# 动态切换数据源

## 单元测试类： com.nydia.modules.UnitTest

## 主要思路

AbstractRoutingDataSource抽象类知识，实现AOP动态切换的关键

AbstractRoutingDataSource中determineTargetDataSource()方法中获取数据源 

setDefaultTargetDataSource 设置默认数据源

setTargetDataSources 设置数据源列表 