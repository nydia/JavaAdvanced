## MySQL 插入数据性能

### 各种插入方法的结果

|量级     | 方式  | 耗时  |
|  ----  | ----  | ----  |
| 100w  | Spring框架+Druid+单线程 | 时间太久不具有参考性 |
| 100w  | Spring框架+Druid+100个线程| 4754663(毫秒),4755(秒),79(分) |
| 100w  | Jdbc+Statement + (url带rewriteBatchedStatements=true) | 125091(毫秒),125(秒),2(分) |
| 100w  | Jdbc+Statement + (url带rewriteBatchedStatements=true) + Druid | 106345(毫秒),106(秒),1.5(分) |
| 100w  | Jdbc+Statement + (url带rewriteBatchedStatements=true) + Druid(10) + 10线程 | 57889(毫秒),58(秒),1(分) |
| 100w  | Jdbc+Statement| 221600(毫秒),221(秒),3.6(分) |
| 100w  | Jdbc+PreparedStatement(url + rewriteBatchedStatements=true)| 210226(毫秒),210(秒),3.5(分) |
