use pentos;
CREATE TABLE `be_simulator_dz` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bank_no` varchar(10) NOT NULL COMMENT '银行编号',
  `path` varchar(255) NOT NULL COMMENT '对账文件路径',
  `trans_type` varchar(10) DEFAULT '' COMMENT '交易类型',
  `id_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `status` varchar(32) NOT NULL DEFAULT 'able' COMMENT '是否禁用, able:已启用, disable:已禁用',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2528 DEFAULT CHARSET=utf8;

CREATE TABLE `be_simulator_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '菜单名称',
  `description` varchar(128) NOT NULL COMMENT '菜单描述',
  `pid` bigint(20) NOT NULL COMMENT '父菜单ID',
  `url` varchar(128) NOT NULL COMMENT '菜单URL',
  `id_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `status` varchar(32) NOT NULL DEFAULT 'able' COMMENT '是否禁用, able:已启用, disable:已禁用',
  `sort` int(11) NOT NULL COMMENT '菜单排序',
  `icon` varchar(128) DEFAULT '' COMMENT '菜单图标的class',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=366 DEFAULT CHARSET=utf8;

CREATE TABLE `be_simulator_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `description` varchar(128) NOT NULL COMMENT '角色描述',
  `id_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `status` varchar(32) NOT NULL DEFAULT 'able' COMMENT '是否禁用, able:已启用, disable:已禁用',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `be_simulator_role_menu` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`menu_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `be_simulator_transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `be_ser` varchar(100) DEFAULT '' COMMENT 'be流水号',
  `bs_ser` varchar(32) DEFAULT '' COMMENT 'bs流水号',
  `mer_id` varchar(64) DEFAULT '' COMMENT '商户号',
  `bank_no` varchar(10) DEFAULT '' COMMENT '银行编号',
  `acco_no` varchar(64) DEFAULT '' COMMENT '交易账号',
  `trans_type` varchar(15) DEFAULT '' COMMENT '交易类型',
  `amount` varchar(32) DEFAULT '0' COMMENT '交易金额',
  `resp_code` varchar(20) DEFAULT '' COMMENT '响应码',
  `trans_stat` varchar(20) DEFAULT '' COMMENT '状态码',
  `stat` varchar(20) DEFAULT '' COMMENT '内部码',
  `work_day` varchar(8) DEFAULT '' COMMENT '工作日',
  `send_time` varchar(20) DEFAULT '' COMMENT '交易时间(be方)',
  `id_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `status` varchar(32) NOT NULL DEFAULT 'able' COMMENT '是否禁用, able:已启用, disable:已禁用',
  `created_date` varchar(8) NOT NULL COMMENT '创建日期(bs方)',
  `created_at` datetime NOT NULL COMMENT '创建时间(bs方)',
  `updated_at` datetime NOT NULL COMMENT '最后更新时间',
  `protocolNo` varchar(45) DEFAULT NULL COMMENT '协议号',
  `memo1` varchar(45) DEFAULT NULL COMMENT '预留字段',
  `memo2` varchar(45) DEFAULT NULL COMMENT '预留字段',
  `IDNUM` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_be_ser` (`be_ser`)
) ENGINE=InnoDB AUTO_INCREMENT=543327 DEFAULT CHARSET=utf8;

CREATE TABLE `be_simulator_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(128) NOT NULL COMMENT '密码盐',
  `realname` varchar(32) DEFAULT '' COMMENT '真实姓名',
  `email` varchar(64) DEFAULT '' COMMENT '电子邮箱',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机号',
  `id_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  `status` varchar(32) NOT NULL DEFAULT 'able' COMMENT '是否禁用, able:已启用, disable:已禁用',
  `small_avatar` varchar(255) DEFAULT '' COMMENT '小头像',
  `medium_avatar` varchar(255) DEFAULT '' COMMENT '中头像',
  `large_avatar` varchar(255) DEFAULT '' COMMENT '大头像',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  `updated_at` datetime NOT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `be_simulator_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sim_bank_channel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `bnk_co` varchar(12) NOT NULL COMMENT '银行代码',
  `bnk_nm` varchar(16) NOT NULL COMMENT '银行名称',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `bnk_no_UNIQUE` (`bnk_co`),
  KEY `create_ix` (`created_time`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='银行通道表';

CREATE TABLE `city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sim_bank_command` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `bnk_co` varchar(12) NOT NULL COMMENT '银行代码',
  `bnk_nm` varchar(16) NOT NULL COMMENT '银行名称',
  `mer_serial_no` varchar(20) NOT NULL COMMENT '商户流水号',
  `bnk_serial_no` varchar(32) NOT NULL COMMENT '银行流水号',
  `tran_co` varchar(16) NOT NULL COMMENT '交易代码',
  `tran_nm` varchar(32) NOT NULL COMMENT '交易名称',
  `mer_date` varchar(8) NOT NULL DEFAULT '' COMMENT '商户交易日期',
  `mer_time` varchar(6) NOT NULL DEFAULT '' COMMENT '商户交易时间',
  `cur_co` varchar(3) NOT NULL DEFAULT '' COMMENT '币种',
  `sndr_acco_no` varchar(64) NOT NULL DEFAULT '' COMMENT '付款方账号',
  `sndr_acco_nm` varchar(64) NOT NULL DEFAULT '' COMMENT '付款方账号名称',
  `sndr_id_tp` varchar(10) NOT NULL DEFAULT '' COMMENT '付款方证件类型',
  `sndr_id_no` varchar(35) NOT NULL DEFAULT '' COMMENT '付款方证件号码',
  `sndr_resv1` varchar(32) NOT NULL DEFAULT '' COMMENT '付款方保留字段1',
  `sndr_resv2` varchar(128) NOT NULL DEFAULT '' COMMENT '付款方保留字段2',
  `amount` varchar(32) NOT NULL DEFAULT '' COMMENT '交易金额',
  `fee_amt` varchar(32) NOT NULL DEFAULT '' COMMENT '手续费',
  `tran_purpose` varchar(320) NOT NULL DEFAULT '' COMMENT '转账用途',
  `tran_remark` varchar(320) NOT NULL DEFAULT '' COMMENT '转账附言',
  `rcvr_acco_no` varchar(64) NOT NULL DEFAULT '' COMMENT '收款方账号',
  `rcvr_acct_nm` varchar(64) NOT NULL DEFAULT '' COMMENT '收款方账号名称',
  `rcvr_id_tp` varchar(10) NOT NULL DEFAULT '' COMMENT '收款方证件类型',
  `rcvr_id_no` varchar(35) NOT NULL DEFAULT '' COMMENT '收款方证件号码',
  `rcvr_resv1` varchar(32) NOT NULL DEFAULT '' COMMENT '收款方保留字段1',
  `rcvr_resv2` varchar(128) NOT NULL DEFAULT '' COMMENT '收款方保留字段2',
  `work_day` varchar(8) NOT NULL DEFAULT '' COMMENT '工作日(对账时使用)',
  `protocol_no` varchar(32) NOT NULL DEFAULT '' COMMENT '协议号',
  `resp_co` varchar(32) NOT NULL COMMENT '错误码',
  `resp_msg` varchar(128) NOT NULL COMMENT '错误描述',
  `lock_st` varchar(32) NOT NULL DEFAULT 'N' COMMENT '锁状态:{N:未锁,T:正在交易}',
  `tran_st` varchar(1) NOT NULL COMMENT '交易状态:{Y:成功,F:失败,I:处理中}',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `mer_serial_no_UNIQUE` (`mer_serial_no`),
  UNIQUE KEY `bnk_serial_no_UNIQUE` (`bnk_serial_no`),
  KEY `create_ix` (`created_time`),
  KEY `tran_co_ix` (`tran_co`),
  KEY `work_day_ix` (`work_day`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='交易流水表';

CREATE TABLE `sim_bank_resp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `bnk_co` varchar(12) NOT NULL COMMENT '银行代码',
  `bnk_nm` varchar(16) NOT NULL COMMENT '银行名称',
  `resp_co` varchar(32) NOT NULL COMMENT '错误码',
  `resp_msg` varchar(128) NOT NULL COMMENT '错误描述',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `bnk_resp_UNIQUE` (`bnk_co`,`resp_co`),
  KEY `create_ix` (`created_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='错误码表';

CREATE TABLE `sim_bank_tran` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `bnk_co` varchar(12) NOT NULL COMMENT '银行代码',
  `bnk_nm` varchar(16) NOT NULL COMMENT '银行名称',
  `tran_co` varchar(16) NOT NULL COMMENT '交易代码',
  `tran_nm` varchar(32) NOT NULL COMMENT '交易名称',
  `resp_co` varchar(32) NOT NULL DEFAULT '' COMMENT '错误码',
  `resp_msg` varchar(128) NOT NULL DEFAULT '' COMMENT '错误描述',
  `dz_temp` varchar(512) NOT NULL DEFAULT '' COMMENT '对账文件模板',
  `is_gen_dz` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否生成对账文件:{0:不生成, 1:生成}',
  `is_push_dz` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否推送对账文件:{0:不推送, 1:推送}',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `create_ix` (`created_time`),
  KEY `bnk_tran_UNIQUE` (`bnk_co`,`tran_co`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8 COMMENT='交易类型表';

CREATE TABLE `sim_dz_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `bnk_co` varchar(12) NOT NULL COMMENT '银行代码',
  `bnk_nm` varchar(16) NOT NULL COMMENT '银行名称',
  `tran_co` varchar(16) NOT NULL COMMENT '交易代码',
  `tran_nm` varchar(32) NOT NULL COMMENT '交易名称',
  `file_path` varchar(256) NOT NULL DEFAULT '' COMMENT '对账文件路径',
  `is_push_dz` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否推送对账文件:{0:不推送, 1:推送}',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `create_ix` (`created_time`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='对账文件表';

CREATE TABLE `sim_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `code` varchar(32) NOT NULL COMMENT '菜单代码',
  `name` varchar(32) NOT NULL COMMENT '菜单名称',
  `pcode` varchar(32) NOT NULL DEFAULT '' COMMENT '父菜单代码',
  `url` varchar(128) NOT NULL DEFAULT '' COMMENT '菜单地址',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '菜单排序(从0开始)',
  `icon` varchar(128) NOT NULL DEFAULT '' COMMENT '菜单图标的样式',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `create_ix` (`created_time`),
  KEY `sort_ix` (`sort`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='菜单表';

CREATE TABLE `sim_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` varchar(16) DEFAULT NULL,
  `bank_card_no` varchar(32) DEFAULT NULL,
  `cert_no` varchar(20) DEFAULT NULL,
  `cert_type` varchar(2) DEFAULT NULL,
  `charge_type` varchar(1) DEFAULT NULL,
  `cust_type` varchar(1) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `payment_type` varchar(1) DEFAULT NULL,
  `prod_id` varchar(32) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `risk_level` varchar(1) DEFAULT NULL,
  `serial_no` varchar(32) DEFAULT NULL,
  `share` varchar(16) DEFAULT NULL,
  `apkind` varchar(3) DEFAULT NULL,
  `trade_acct` varchar(32) DEFAULT NULL,
  `order_type` varchar(1) DEFAULT NULL,
  `company_no` varchar(4) DEFAULT NULL,
  `sub_company_no` varchar(8) DEFAULT NULL,
  `partner_cust_no` varchar(20) DEFAULT NULL,
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sim_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `code` varchar(32) NOT NULL COMMENT '角色代码',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  KEY `create_ix` (`created_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

CREATE TABLE `sim_role_menu` (
  `role_code` varchar(32) NOT NULL COMMENT '角色代码',
  `menu_code` varchar(32) NOT NULL COMMENT '菜单代码',
  PRIMARY KEY (`role_code`,`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

CREATE TABLE `sim_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键, 自增',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `salt` varchar(64) NOT NULL COMMENT '密码盐',
  `fullname` varchar(32) NOT NULL COMMENT '姓名',
  `small_avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '小头像',
  `medium_avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '中头像',
  `large_avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '大头像',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除:{0:未删除, 1:已删除}',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `create_ix` (`created_time`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

CREATE TABLE `sim_user_role` (
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `role_code` varchar(32) NOT NULL COMMENT '角色代码',
  PRIMARY KEY (`username`,`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

CREATE TABLE `th_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` varchar(16) DEFAULT NULL,
  `bank_card_no` varchar(32) DEFAULT NULL,
  `cert_no` varchar(20) DEFAULT NULL,
  `cert_type` varchar(2) DEFAULT NULL,
  `charge_type` varchar(1) DEFAULT NULL,
  `cust_type` varchar(1) DEFAULT NULL,
  `mobile` varchar(11) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `payment_type` varchar(1) DEFAULT NULL,
  `prod_id` varchar(32) DEFAULT NULL,
  `remark` varchar(256) DEFAULT NULL,
  `risk_level` varchar(1) DEFAULT NULL,
  `serial_no` varchar(32) DEFAULT NULL,
  `share` varchar(16) DEFAULT NULL,
  `apkind` varchar(3) DEFAULT NULL,
  `trade_acct` varchar(32) DEFAULT NULL,
  `order_type` varchar(1) DEFAULT NULL,
  `company_no` varchar(4) DEFAULT NULL,
  `sub_company_no` varchar(8) DEFAULT NULL,
  `partner_cust_no` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;