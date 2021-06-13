
## 用户表

```sql
CREATE TABLE `user_base` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Id',
  `login_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '登录名',
  `pick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '密码',
  `card_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '证件类型：11二代身份证/12户口本/13驾照\n/14港澳通行证（关联字典表：证件类型cd_type）',
  `id_card` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '身份证号码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '姓名',
  `sex` enum('1','2') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '性别  1-男/2-女',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '电话',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '手机',
  `integral` int(8) DEFAULT '0' COMMENT '用户积分',
  `photo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '用户头像',
  `login_ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '最后登陆IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `login_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '是否可登录(0-否/1-是)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标记(0-否/1-是)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表'
```

## 商品表

```sql
CREATE TABLE `route_base` (
  `id` varchar(64) NOT NULL COMMENT 'Id',
  `route_code` varchar(255) DEFAULT NULL COMMENT '行程编号',
  `days` int(8) DEFAULT NULL COMMENT '行程天数',
  `travel_nights` int(8) DEFAULT NULL COMMENT '行程有几晚',
  `title` varchar(255) DEFAULT NULL COMMENT '标题（前端展示，字段内容来源：1-根据目的地）',
  `subtitle` varchar(255) DEFAULT NULL COMMENT '副标题',
  `product_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '名称(前端可能不显示，仅供后台使用)',
  `route_brand_id` varchar(64) DEFAULT NULL COMMENT '线路品牌（关联字典表的id，字典未线路品牌route_brand）',
  `virtual_score` float DEFAULT NULL COMMENT '虚拟评分',
  `real_score` float DEFAULT NULL COMMENT '真实评分',
  `virtual_score_flag` enum('0','1') DEFAULT NULL COMMENT '虚拟评分是否启用（0-否/1-是）',
  `virtual_collection` int(8) DEFAULT NULL COMMENT '虚拟收藏数',
  `virtual_collection_flag` enum('0','1') DEFAULT NULL COMMENT '虚拟收藏数是否启用（0-否/1-是）',
  `real_collection` int(8) DEFAULT NULL COMMENT '真实收藏数',
  `id_reserve_flag` enum('0','1') DEFAULT NULL COMMENT '是否需证件号码预订(0-不需要/1-需要)',
  `effect_start` datetime DEFAULT NULL COMMENT '有效期(开始)(暂不启用)',
  `effect_end` datetime DEFAULT NULL COMMENT '有效期(结束)(暂不启用)',
  `start_city_id` varchar(64) DEFAULT NULL COMMENT '出发城市（关联地区表sys_area的id）',
  `end_city_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '目的城市（关联地区表sys_area的id）',
  `transport_id` varchar(64) DEFAULT NULL COMMENT '交通工具（关联字典表的交通工具--transports）',
  `label_ids` varchar(1000) DEFAULT NULL,
  `country_scenic_ids` varchar(255) DEFAULT NULL COMMENT '国家景区（多个以英文半角逗号 分割）',
  `route_type_id` varchar(64) DEFAULT NULL COMMENT '行程类型（关联行程类型表的id--route_type）',
  `shuttle_flag` enum('0','1') DEFAULT NULL COMMENT '接送（0-不需要/1-需要）',
  `shuttle_remark` varchar(255) DEFAULT NULL COMMENT '接送备注',
  `scenic_ids` varchar(255) DEFAULT NULL COMMENT '行程景区（景区有先后顺序，以英文逗号分隔）（在选择行程编辑方式为富文本的时候用）',
  `enroll_early` int(8) DEFAULT NULL COMMENT '建议提前报名天数（单位：天）',
  `brief_web` longblob COMMENT '推荐简要描述(web端)（数据库存储方式：以富文本的形式存储）',
  `brief_app` longblob COMMENT '推荐简要描述(App端)（数据库存储方式：以富文本的形式存储）',
  `sequence` int(8) DEFAULT NULL COMMENT '排序',
  `show_index` enum('0','1') DEFAULT '0' COMMENT '显示在首页   0-否/1-是',
  `pic_urls` varchar(500) DEFAULT NULL COMMENT '行程图片（多个以英文半角逗号,分割）',
  `index_pic_url` varchar(255) DEFAULT NULL COMMENT '行程首页图片',
  `app_recommend_index` enum('0','1') DEFAULT '0' COMMENT '推荐到移动端首页   0-否/1-是',
  `app_recommend` enum('0','1') DEFAULT '0' COMMENT '推荐到移动端   0-否/1-是',
  `app_recommend_sequence` int(8) DEFAULT '1' COMMENT '推荐到移动端排序',
  `product_manager_recommend` longtext COMMENT '产品经理推荐',
  `group_no` varchar(64) DEFAULT NULL COMMENT '分组编码（商品信息复制一份出来后，这些商品属于同一类）',
  `verify_state` enum('1','2','3') DEFAULT NULL COMMENT '审核状态（1-未审核/2-审核中/3-审核通过/4-审核未通过）',
  `release_state` enum('0','1') DEFAULT '0' COMMENT '发布状态  0-未发布/1-发布',
  `trip_edit_mode` enum('1','2') DEFAULT NULL COMMENT '编辑方式 1-按天编辑模式/2-可视化编辑模式\\n如果是编辑方式2，trip_context不可以为空',
  `trip_content` longblob COMMENT '行程内容（在选择行程编辑方式为富文本的时候用）',
  `create_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` enum('0','1') CHARACTER SET utf8 COLLATE utf8_bin DEFAULT '0' COMMENT '删除标记（0-否/1-是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='行程基本信息'
```

## 订单表

```sql
CREATE TABLE `order_route` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT 'Id',
  `route_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '线路id',
  `uid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '下单会员（关联会员表user_base的id）',
  `order_channel_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单渠道id（关联渠道id）',
  `order_platform` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单来源平台：1-web/2-app/3-微信（关联字典表类型platform）',
  `order_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单名称',
  `order_time` datetime DEFAULT NULL COMMENT '下单时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `pay_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '支付方式：weixin-微信/-alipay支付宝（关联字典表支付方式pay_type）',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `request_refund_time` datetime DEFAULT NULL COMMENT '申请退款时间',
  `refund_time` datetime DEFAULT NULL COMMENT '退款时间',
  `refund_mode` enum('1','2') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '退款方式 （1-全额退款/2-部分退款）',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `fail_time` datetime DEFAULT NULL COMMENT '未付款订单失效时间',
  `valid_time` int(8) DEFAULT NULL COMMENT '未付款订单有效时长(单位：分钟；不启用，从字典取值，order_valid_time)',
  `order_type_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单类型    关联行程类型trip_type表的id（1普通线路/2拼团/3小包团）',
  `order_status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '订单状态\n/unpaid待支付\n/cancelled已取消\n/paid已支付\n/unused未使用（已付款）\n/ontrip出行中\n/norefund申请退款中\n/refuned已退款\n\n评价状态从评价表里面取值',
  `invoice_no` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否开发票  1-是/0-否',
  `unit_adult_price` decimal(10,2) DEFAULT NULL COMMENT '成人售价',
  `unit_child_price` decimal(10,2) DEFAULT NULL COMMENT '儿童售价',
  `activity_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '参加活动信息',
  `user_coupon_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '优惠券id',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '总金额',
  `discount_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '实付金额',
  `route_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '行程名称',
  `ispinfang` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否同意拼房：1是/0否',
  `single_room_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '是否有单房差：1是/0无',
  `single_room_price` decimal(10,2) DEFAULT NULL COMMENT '单人房差价',
  `room_num` int(8) DEFAULT NULL COMMENT '房间数',
  `room_differ_total` decimal(10,2) DEFAULT NULL COMMENT '房差总金额',
  `insurance_total` decimal(10,2) DEFAULT NULL COMMENT '保险总金额',
  `adult_num` int(8) DEFAULT NULL COMMENT '成人数',
  `child_num` int(8) DEFAULT NULL COMMENT '儿童数',
  `order_route_status` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '线路订单状态\n：no无效/\nnosure待确认/\nsure已确认\n/group成团\n/start出团\n/finish已完成/\nassess待评价\n/end已评价（关联字典线路订单状态order_route_status）\n',
  `start_date` datetime DEFAULT NULL COMMENT '出行时间',
  `end_date` datetime DEFAULT NULL COMMENT '回团时间',
  `contact` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联络人',
  `contact_mobile` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人手机号',
  `contact_email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系人邮箱',
  `pick_airport_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接机航班',
  `send_airport_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '送机航班',
  `pick_train_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '接站车次',
  `send_train_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '送站车次',
  `line_order_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '留言',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '备注信息',
  `del_flag` enum('0','1') CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标记  0-否/1-是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='行程订单'
```