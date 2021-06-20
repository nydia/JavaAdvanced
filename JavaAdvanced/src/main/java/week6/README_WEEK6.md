```sql
/* 商品 Table structure for table `geek_good` */

DROP TABLE IF EXISTS `geek_good`;

CREATE TABLE `geek_good` (
  `good_no` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '编码',
  `name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `type` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分类',
  `weight` float(10,2) DEFAULT NULL COMMENT '重量',
  PRIMARY KEY (`good_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='商品';

/*订单 Table structure for table `geek_order` */

DROP TABLE IF EXISTS `geek_order`;

CREATE TABLE `geek_order` (
  `order_no` varchar(64) COLLATE utf8mb4_bin NOT NULL COMMENT '订单编号',
  `amt` float(10,2) DEFAULT NULL COMMENT '金额',
  `status` varchar(4) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '状态',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单';

/*订单商品 Table structure for table `geek_order_good` */

DROP TABLE IF EXISTS `geek_order_good`;

CREATE TABLE `geek_order_good` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `order_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单编号',
  `good_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='订单商品';

/*购物车商品 Table structure for table `geek_shop_card_good` */

DROP TABLE IF EXISTS `geek_shop_card_good`;

CREATE TABLE `geek_shop_card_good` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一id',
  `shop_cart_id` bigint(20) DEFAULT NULL COMMENT '购物车id',
  `good_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='购物车商品';

/* 购物车 Table structure for table `geek_shop_cart` */

DROP TABLE IF EXISTS `geek_shop_cart`;

CREATE TABLE `geek_shop_cart` (
  `id` int(11) NOT NULL COMMENT '购物车id',
  `total_amt` float(10,2) DEFAULT NULL COMMENT '总价',
  `discount_amt` float(10,2) DEFAULT NULL COMMENT '优惠价',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='购物车';

/*供应商 Table structure for table `geek_supplier` */

DROP TABLE IF EXISTS `geek_supplier`;

CREATE TABLE `geek_supplier` (
  `supplier_id` bigint(20) NOT NULL COMMENT '唯一id',
  `supplier_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '名称',
  `code` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '注册号',
  `contact` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人',
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='供应商';

/*供应商-商品 Table structure for table `geek_supplier_good` */

DROP TABLE IF EXISTS `geek_supplier_good`;

CREATE TABLE `geek_supplier_good` (
  `id` bigint(20) DEFAULT NULL COMMENT '唯一id',
  `supplier_id` bigint(20) DEFAULT NULL COMMENT '供应商id',
  `good_no` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '商品编号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='供应商-商品';

/*用户 Table structure for table `geek_user` */

DROP TABLE IF EXISTS `geek_user`;

CREATE TABLE `geek_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `id_card` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户';

```
