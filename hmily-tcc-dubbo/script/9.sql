-- create schema hmily;
-- create schema demo_ds_0;
-- create schema demo_ds_1;


-- demo_ds_0 表初始化

USE `demo_ds_0`;

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(128) NOT NULL,
  `account_no` varchar(128) DEFAULT '0' COMMENT '账户号',
  `account_type` varchar(20) DEFAULT '0' COMMENT '账户类型 RMB-人民币 / USD-美元',
  `balance` decimal(10,0) DEFAULT 0 COMMENT '用户余额',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert  into `account`(`id`,`user_id`,`account_no`,`account_type`,`balance`,`create_time`,`update_time`) values (1,'10000','10000','RMB', 10000000,'2017-09-18 14:54:22',NULL);
insert  into `account`(`id`,`user_id`,`account_no`,`account_type`,`balance`,`create_time`,`update_time`) values (2,'10000','20000','USD', 10000000,'2017-09-18 14:54:22',NULL);

DROP TABLE IF EXISTS `account_freeze`;

CREATE TABLE `account_freeze` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一性id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户id号',
  `account_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '账户号',
  `account_type` varchar(20) DEFAULT '0' COMMENT '账户类型 RMB-人民币 / USD-美元',
  `freeze_amt` decimal(10,0) DEFAULT 0 COMMENT '冻结金额',
  `tran_no` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '交易流水号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='冻结资产表';

insert into `account_freeze` (`id`, `user_id`, `account_no`, `account_type`, `freeze_amt`, `tran_no`, `create_time`, `update_time`) values('1','10000','10000','RMB','0','','2021-07-05 17:36:34','2021-07-05 17:36:34');
insert into `account_freeze` (`id`, `user_id`, `account_no`, `account_type`, `freeze_amt`, `tran_no`, `create_time`, `update_time`) values('2','10000','20000','USD','0','','2021-07-05 17:36:40','2021-07-05 17:36:40');

-- demo_ds_1 表初始化

USE `demo_ds_1`;

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`user_id` varchar(128) NOT NULL,
`account_no` varchar(128) DEFAULT '0' COMMENT '账户号',
`account_type` varchar(20) DEFAULT '0' COMMENT '账户类型 RMB-人民币 / USD-美元',
`balance` decimal(10,0) DEFAULT 0 COMMENT '用户余额',
`create_time` datetime NOT NULL,
`update_time` datetime DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

insert  into `account`(`id`,`user_id`,`account_no`,`account_type`,`balance`,`create_time`,`update_time`) values (1,'20000','10000','RMB', 10000000,'2017-09-18 14:54:22',NULL);
insert  into `account`(`id`,`user_id`,`account_no`,`account_type`,`balance`,`create_time`,`update_time`) values (2,'10000','20000','USD', 10000000,'2017-09-18 14:54:22',NULL);

DROP TABLE IF EXISTS `account_freeze`;

CREATE TABLE `account_freeze` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '唯一性id',
  `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '用户id号',
  `account_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '账户号',
  `account_type` varchar(20) DEFAULT '0' COMMENT '账户类型 RMB-人民币 / USD-美元',
  `freeze_amt` decimal(10,0) DEFAULT 0 COMMENT '冻结金额',
  `tran_no` varchar(255) COLLATE utf8mb4_bin DEFAULT '' COMMENT '交易流水号',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='冻结资产表';

insert into `account_freeze` (`id`, `user_id`, `account_no`, `account_type`, `freeze_amt`, `tran_no`, `create_time`, `update_time`) values('1','20000','10000','RMB','0','','2021-07-05 17:36:34','2021-07-05 17:36:34');
insert into `account_freeze` (`id`, `user_id`, `account_no`, `account_type`, `freeze_amt`, `tran_no`, `create_time`, `update_time`) values('2','20000','20000','USD','0','','2021-07-05 17:36:40','2021-07-05 17:36:40');