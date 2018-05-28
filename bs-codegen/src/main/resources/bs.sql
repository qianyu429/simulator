CREATE TABLE `transaction` (
  `id`           BIGINT      NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  `be_ser`       VARCHAR(32) NULL     DEFAULT ''
  COMMENT 'be流水号',
  `bs_ser`       VARCHAR(32) NULL     DEFAULT ''
  COMMENT 'bs流水号',
  `mer_id`       VARCHAR(64) NULL     DEFAULT ''
  COMMENT '商户号',
  `bank_no`      VARCHAR(10) NULL     DEFAULT ''
  COMMENT '银行编号',
  `acco_no`      VARCHAR(64) NULL     DEFAULT ''
  COMMENT '交易账号',
  `trans_type`   VARCHAR(10) NULL     DEFAULT ''
  COMMENT '交易类型',
  `amount`       VARCHAR(32) NULL     DEFAULT '0'
  COMMENT '交易金额',
  `resp_code`    VARCHAR(20) NULL     DEFAULT ''
  COMMENT '响应码',
  `trans_stat`   VARCHAR(20) NULL     DEFAULT ''
  COMMENT '状态码',
  `stat`         VARCHAR(20) NULL     DEFAULT ''
  COMMENT '内部码',
  `work_day`     VARCHAR(8)  NULL     DEFAULT ''
  COMMENT '工作日',
  `send_time`    VARCHAR(20) NULL     DEFAULT ''
  COMMENT '交易时间(be方)',
  `status`       VARCHAR(32) NOT NULL DEFAULT 'able'
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  `created_date` VARCHAR(8)  NOT NULL
  COMMENT '创建日期(bs方)',
  `created_time` DATETIME    NOT NULL
  COMMENT '创建时间(bs方)',
  `updated_time` DATETIME    NOT NULL
  COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `be_ser_UNIQUE` (`be_ser` ASC)
)
  COMMENT '交易表';

CREATE TABLE `dz` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  `bank_no`      VARCHAR(10)  NOT NULL
  COMMENT '银行编号',
  `path`         VARCHAR(255) NOT NULL
  COMMENT '对账文件路径',
  `trans_type`   VARCHAR(10)  NULL     DEFAULT ''
  COMMENT '交易类型',
  `status`       VARCHAR(32)  NOT NULL DEFAULT 'able'
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  `created_time` DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_time` DATETIME     NOT NULL
  COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
)
  COMMENT '对账信息表';

CREATE TABLE `bank` (
  `id`              BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  `bank_no`         VARCHAR(10)  NOT NULL
  COMMENT '银行编号',
  `bank_name`       VARCHAR(32)  NOT NULL
  COMMENT '银行名称',
  `mer_id`          VARCHAR(64)  NOT NULL
  COMMENT '商户号',
  `code`            VARCHAR(10)  NOT NULL
  COMMENT '银行代码',
  `pay_head`        VARCHAR(255) NULL     DEFAULT ''
  COMMENT '代扣对账文件第一行模板',
  `pay_template`    VARCHAR(255) NULL     DEFAULT ''
  COMMENT '代扣对账文件从第二行起的行模板',
  `pay_url`         VARCHAR(255) NULL     DEFAULT ''
  COMMENT '代扣对账回调地址',
  `redeem_head`     VARCHAR(255) NULL     DEFAULT ''
  COMMENT '代付对账文件第一行模板',
  `redeem_template` VARCHAR(255) NULL     DEFAULT ''
  COMMENT '代付对账文件从第二行起的行模板',
  `redeem_url`      VARCHAR(255) NULL     DEFAULT ''
  COMMENT '代付对账回调地址',
  `admin_user_id`   BIGINT       NULL     DEFAULT 0
  COMMENT '银行管理员ID',
  `admin_realname`  VARCHAR(64)  NULL     DEFAULT ''
  COMMENT '银行管理员姓名',
  `status`          VARCHAR(32)  NOT NULL DEFAULT 'able'
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  `created_time`    DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_time`    DATETIME     NOT NULL
  COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `bank_no_UNIQUE` (`bank_no` ASC)
)
  COMMENT '银行信息表';

INSERT INTO bank
(bank_no, bank_name, mer_id, code, pay_head, pay_template, pay_url, redeem_head, redeem_template, redeem_url, admin_user_id, admin_realname, status, created_time, updated_time)
VALUES
  ('999', '中国银联', '808080211580085', 'cp', '%s|%s|%s|%s|%s|%s',
          'bankNo|workDay|bsSer|transStat|respCode|null|accoNo|amount|null|null', '', '%s|%s|%s|%s|%s|%s|%s|%s|%s',
          'merId|workDay|beSer|createdDate|bsSer|accoNo|null|amount|null|transStat|null|null|null|null', '', 2, '银联管理员',
   'able',
   '2016-05-29 16:12:46', '2016-05-29 16:12:49');

INSERT INTO config
(user_id, grp, k, val, status, created_time, updated_time)
VALUES
  ('0', '', 'serNo', '100000', 'able', '2016-05-26 15:08:48', '2016-05-26 15:08:48');

ALTER TABLE be_simulator_transaction ADD INDEX be_ser (be_ser);