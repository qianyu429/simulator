CREATE TABLE be_simulator_bank
(
  id              BIGINT(20) PRIMARY KEY     NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  bank_no         VARCHAR(10)                NOT NULL
  COMMENT '银行编号',
  bank_name       VARCHAR(32)                NOT NULL
  COMMENT '银行名称',
  mer_id          VARCHAR(64)                NOT NULL
  COMMENT '商户号',
  code            VARCHAR(10)                NOT NULL
  COMMENT '银行代码',
  pay_head        VARCHAR(255)                        DEFAULT ''
  COMMENT '代扣对账文件第一行模板',
  pay_template    VARCHAR(255)                        DEFAULT ''
  COMMENT '代扣对账文件从第二行起的行模板',
  pay_url         VARCHAR(255)                        DEFAULT ''
  COMMENT '代扣对账回调地址',
  redeem_head     VARCHAR(255)                        DEFAULT ''
  COMMENT '代付对账文件第一行模板',
  redeem_template VARCHAR(255)                        DEFAULT ''
  COMMENT '代付对账文件从第二行起的行模板',
  redeem_url      VARCHAR(255)                        DEFAULT ''
  COMMENT '代付对账回调地址',
  admin_user_id   BIGINT(20)                          DEFAULT '0'
  COMMENT '银行管理员ID',
  admin_realname  VARCHAR(64)                         DEFAULT ''
  COMMENT '银行管理员姓名',
  id_delete       TINYINT(4)                 NULL     DEFAULT 0
  COMMENT '是否删除',
  status          VARCHAR(32) DEFAULT 'able' NOT NULL
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  created_at    DATETIME                   NOT NULL
  COMMENT '创建时间',
  updated_at    DATETIME                   NOT NULL
  COMMENT '最后更新时间'
);
CREATE UNIQUE INDEX bank_no_UNIQUE ON be_simulator_bank (bank_no);
CREATE UNIQUE INDEX id_UNIQUE ON be_simulator_bank (id);
CREATE TABLE be_simulator_config
(
  id           BIGINT(20) PRIMARY KEY     NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  user_id      BIGINT(20)                 NOT NULL
  COMMENT '配置所属的用户',
  grp          VARCHAR(32)                         DEFAULT ''
  COMMENT '组',
  k            VARCHAR(32)                         DEFAULT ''
  COMMENT '键',
  val          VARCHAR(255)               NOT NULL
  COMMENT '值',
  id_delete    TINYINT(4)                 NULL     DEFAULT 0
  COMMENT '是否删除',
  status       VARCHAR(32) DEFAULT 'able' NOT NULL
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  created_at DATETIME                   NOT NULL
  COMMENT '创建时间',
  updated_at DATETIME                   NOT NULL
  COMMENT '最后更新时间'
);
CREATE UNIQUE INDEX id_UNIQUE ON be_simulator_config (id);
CREATE TABLE be_simulator_dz
(
  id           BIGINT(20) PRIMARY KEY     NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  bank_no      VARCHAR(10)                NOT NULL
  COMMENT '银行编号',
  path         VARCHAR(255)               NOT NULL
  COMMENT '对账文件路径',
  trans_type   VARCHAR(10)                         DEFAULT ''
  COMMENT '交易类型',
  id_delete    TINYINT(4)                 NULL     DEFAULT 0
  COMMENT '是否删除',
  status       VARCHAR(32) DEFAULT 'able' NOT NULL
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  created_at DATETIME                   NOT NULL
  COMMENT '创建时间',
  updated_at DATETIME                   NOT NULL
  COMMENT '最后更新时间'
);
CREATE UNIQUE INDEX id_UNIQUE ON be_simulator_dz (id);
CREATE TABLE be_simulator_menu
(
  id           BIGINT(20) PRIMARY KEY     NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  name         VARCHAR(64)                NOT NULL
  COMMENT '菜单名称',
  description  VARCHAR(128)               NOT NULL
  COMMENT '菜单描述',
  pid          BIGINT(20)                 NOT NULL
  COMMENT '父菜单ID',
  url          VARCHAR(128)               NOT NULL
  COMMENT '菜单URL',
  id_delete    TINYINT(4)                 NULL     DEFAULT 0
  COMMENT '是否删除',
  status       VARCHAR(32) DEFAULT 'able' NOT NULL
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  sort         INT(11)                    NOT NULL
  COMMENT '菜单排序',
  icon         VARCHAR(128)                        DEFAULT ''
  COMMENT '菜单图标的class',
  created_at DATETIME                   NOT NULL
  COMMENT '创建时间',
  updated_at DATETIME                   NOT NULL
  COMMENT '最后更新时间'
);
CREATE UNIQUE INDEX id_UNIQUE ON be_simulator_menu (id);
CREATE UNIQUE INDEX name_UNIQUE ON be_simulator_menu (name);
CREATE UNIQUE INDEX url_UNIQUE ON be_simulator_menu (url);
CREATE TABLE be_simulator_role
(
  id           BIGINT(20) PRIMARY KEY     NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  name         VARCHAR(64)                NOT NULL
  COMMENT '角色名称',
  description  VARCHAR(128)               NOT NULL
  COMMENT '角色描述',
  id_delete    TINYINT(4)                 NULL     DEFAULT 0
  COMMENT '是否删除',
  status       VARCHAR(32) DEFAULT 'able' NOT NULL
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  created_at DATETIME                   NOT NULL
  COMMENT '创建时间',
  updated_at DATETIME                   NOT NULL
  COMMENT '最后更新时间'
);
CREATE UNIQUE INDEX id_UNIQUE ON be_simulator_role (id);
CREATE UNIQUE INDEX name_UNIQUE ON be_simulator_role (name);
CREATE TABLE be_simulator_role_menu
(
  role_id BIGINT(20) NOT NULL
  COMMENT '角色ID',
  menu_id BIGINT(20) NOT NULL
  COMMENT '菜单ID',
  CONSTRAINT `PRIMARY` PRIMARY KEY (role_id, menu_id)
);
CREATE TABLE be_simulator_transaction
(
  id           BIGINT(20) PRIMARY KEY     NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  be_ser       VARCHAR(32)                         DEFAULT ''
  COMMENT 'be流水号',
  bs_ser       VARCHAR(32)                         DEFAULT ''
  COMMENT 'bs流水号',
  mer_id       VARCHAR(64)                         DEFAULT ''
  COMMENT '商户号',
  bank_no      VARCHAR(10)                         DEFAULT ''
  COMMENT '银行编号',
  acco_no      VARCHAR(64)                         DEFAULT ''
  COMMENT '交易账号',
  trans_type   VARCHAR(10)                         DEFAULT ''
  COMMENT '交易类型',
  amount       VARCHAR(32)                         DEFAULT '0'
  COMMENT '交易金额',
  resp_code    VARCHAR(20)                         DEFAULT ''
  COMMENT '响应码',
  trans_stat   VARCHAR(20)                         DEFAULT ''
  COMMENT '状态码',
  stat         VARCHAR(20)                         DEFAULT ''
  COMMENT '内部码',
  work_day     VARCHAR(8)                          DEFAULT ''
  COMMENT '工作日',
  send_time    VARCHAR(20)                         DEFAULT ''
  COMMENT '交易时间(be方)',
  id_delete    TINYINT(4)                 NULL     DEFAULT 0
  COMMENT '是否删除',
  status       VARCHAR(32) DEFAULT 'able' NOT NULL
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  created_date VARCHAR(8)                 NOT NULL
  COMMENT '创建日期(bs方)',
  created_at DATETIME                   NOT NULL
  COMMENT '创建时间(bs方)',
  updated_at DATETIME                   NOT NULL
  COMMENT '最后更新时间'
);
CREATE UNIQUE INDEX be_ser_UNIQUE ON be_simulator_transaction (be_ser);
CREATE UNIQUE INDEX id_UNIQUE ON be_simulator_transaction (id);
CREATE TABLE be_simulator_user
(
  id            BIGINT(20) PRIMARY KEY     NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  username      VARCHAR(64)                NOT NULL
  COMMENT '用户名',
  password      VARCHAR(64)                NOT NULL
  COMMENT '密码',
  salt          VARCHAR(128)               NOT NULL
  COMMENT '密码盐',
  realname      VARCHAR(32)                         DEFAULT ''
  COMMENT '真实姓名',
  email         VARCHAR(64)                         DEFAULT ''
  COMMENT '电子邮箱',
  mobile        VARCHAR(20)                         DEFAULT ''
  COMMENT '手机号',
  id_delete     TINYINT(4)                 NULL     DEFAULT 0
  COMMENT '是否删除',
  status        VARCHAR(32) DEFAULT 'able' NOT NULL
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  small_avatar  VARCHAR(255)                        DEFAULT ''
  COMMENT '小头像',
  medium_avatar VARCHAR(255)                        DEFAULT ''
  COMMENT '中头像',
  large_avatar  VARCHAR(255)                        DEFAULT ''
  COMMENT '大头像',
  created_at  DATETIME                   NOT NULL
  COMMENT '创建时间',
  updated_at  DATETIME                   NOT NULL
  COMMENT '最后更新时间'
);
CREATE UNIQUE INDEX id_UNIQUE ON be_simulator_user (id);
CREATE UNIQUE INDEX username_UNIQUE ON be_simulator_user (username);
CREATE TABLE be_simulator_user_role
(
  user_id BIGINT(20) NOT NULL
  COMMENT '用户ID',
  role_id BIGINT(20) NOT NULL
  COMMENT '角色ID',
  CONSTRAINT `PRIMARY` PRIMARY KEY (user_id, role_id)
);

INSERT INTO be.be_simulator_bank (id, bank_no, bank_name, mer_id, code, pay_head, pay_template, pay_url, redeem_head, redeem_template, redeem_url, admin_user_id, admin_realname, status, created_at, updated_at)
VALUES (1, '999', '中国银联', '808080211580085', 'cp', '%s|%s|%s|%s|%s|%s',
           'bankNo|workDay|beSer|transStat|respCode|null|accoNo|amount|null|null|bsSer',
           'http://10.199.101.212:8080/bankEngine/stmt/downloadCpPayStmt.service', '%s|%s|%s|%s|%s|%s|%s|%s|%s',
           'merId|workDay|beSer|createdDate|bsSer|accoNo|null|amount|null|transStat|null|null|null|beSer',
           'http://10.199.101.212:8080/bankEngine/stmt/downloadCpRedeemStmt.service', 2, '银联管理员', 'able',
        '2016-05-29 16:12:46', '2016-05-31 10:48:18');
INSERT INTO be.be_simulator_bank (id, bank_no, bank_name, mer_id, code, pay_head, pay_template, pay_url, redeem_head, redeem_template, redeem_url, admin_user_id, admin_realname, status, created_at, updated_at)
VALUES (2, '599', '上海证通', '12398765432345', 'ect', '', '', '', '', '', '', 3, '证通管理员', 'able', '2016-05-30 17:13:50',
        '2016-05-30 17:14:50');

INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (1, 0, 'skin', 'no-skin', '#438EB9', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (2, 0, 'sys', 'navbar', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (3, 0, 'sys', 'sidebar', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (4, 0, 'sys', 'breadcrumbs', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (5, 0, 'sys', 'container', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (6, 0, 'sys', 'hover', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (7, 0, 'sys', 'compact', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (8, 0, 'sys', 'highlight', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (9, 1, 'skin', 'no-skin', '#438EB9', 'able', '2016-05-18 18:52:06', '2016-05-30 15:36:30');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (10, 1, 'sys', 'navbar', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (11, 1, 'sys', 'sidebar', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (12, 1, 'sys', 'breadcrumbs', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (13, 1, 'sys', 'container', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (14, 1, 'sys', 'hover', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (15, 1, 'sys', 'compact', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (16, 1, 'sys', 'highlight', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (17, 2, 'skin', 'no-skin', '#438EB9', 'able', '2016-05-26 14:16:04', '2016-05-27 10:54:10');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (18, 2, 'sys', 'navbar', '1', 'able', '2016-05-26 14:16:04', '2016-05-26 19:08:04');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (19, 2, 'sys', 'sidebar', '1', 'able', '2016-05-26 14:16:04', '2016-05-26 19:08:04');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (20, 2, 'sys', 'breadcrumbs', '0', 'able', '2016-05-26 14:16:04', '2016-05-26 19:31:15');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (21, 2, 'sys', 'container', '0', 'able', '2016-05-26 14:16:04', '2016-05-26 14:16:04');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (22, 2, 'sys', 'hover', '0', 'able', '2016-05-26 14:16:04', '2016-05-26 14:16:04');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (23, 2, 'sys', 'compact', '0', 'able', '2016-05-26 14:16:04', '2016-05-26 14:16:04');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (24, 2, 'sys', 'highlight', '0', 'able', '2016-05-26 14:16:04', '2016-05-26 14:16:04');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (78, 0, '', 'serNo', '100115', 'able', '2016-05-26 15:08:48', '2016-05-31 10:26:46');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (149, 3, 'skin', 'no-skin', '#438EB9', 'able', '2016-05-30 17:10:40', '2016-05-30 17:10:40');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (150, 3, 'sys', 'navbar', '0', 'able', '2016-05-30 17:10:40', '2016-05-30 17:10:40');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (151, 3, 'sys', 'sidebar', '0', 'able', '2016-05-30 17:10:40', '2016-05-30 17:10:40');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (152, 3, 'sys', 'breadcrumbs', '0', 'able', '2016-05-30 17:10:40', '2016-05-30 17:10:40');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (153, 3, 'sys', 'container', '0', 'able', '2016-05-30 17:10:40', '2016-05-30 17:10:40');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (154, 3, 'sys', 'hover', '0', 'able', '2016-05-30 17:10:40', '2016-05-30 17:10:40');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (155, 3, 'sys', 'compact', '0', 'able', '2016-05-30 17:10:40', '2016-05-30 17:10:40');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (156, 3, 'sys', 'highlight', '0', 'able', '2016-05-30 17:10:40', '2016-05-30 17:10:40');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (157, 0, 'cp-code-pay', '00', '处理完成或接收成功', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (158, 0, 'cp-code-pay', '45', '系统正在对数据处理', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (159, 0, 'cp-code-verify', '000', '交易成功', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (160, 0, 'cp-code-verify', '001', '查发卡方', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (161, 0, 'cp-code-verify', '002', '查发卡方的特殊条件', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (162, 0, 'cp-code-verify', '003', '无效商户', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (163, 0, 'cp-code-verify', '004', '无效卡', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (164, 0, 'cp-code-verify', '005', '不予承兑', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (165, 0, 'cp-code-verify', '006', '交易出错', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (166, 0, 'cp-code-payStat', '1001', '处理完成或接收成功', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (167, 0, 'cp-code-payStat', '2000', '系统正在对数据处理', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (168, 0, 'cp-code-redeem', '0000', '提交成功', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (169, 0, 'cp-code-redeem', '0100', '商户提交的字段长度、格式错误', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (170, 0, 'cp-code-redeem', '0101', '商户验签错误', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (171, 0, 'cp-code-redeem', '0102', '手续费计算出错', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (172, 0, 'cp-code-redeem', '0103', '商户备付金帐户金额不足', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (173, 0, 'cp-code-redeem', '0104', '操作拒绝', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (174, 0, 'cp-code-redeem', '0105', '重复交易', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (175, 0, 'cp-code-redeemStat', 's', '交易成功', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (176, 0, 'cp-code-redeemStat', '2', '交易已接受', 'able', '2016-05-30 22:40:51', '2016-05-31 10:50:15');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (177, 0, 'cp-code-redeemStat', '3', '财务已确认', 'able', '2016-05-30 22:40:51', '2016-05-31 10:50:11');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (178, 0, 'cp-code-redeemStat', '4', '财务处理中', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (179, 0, 'cp-code-redeemStat', '5', '已发往银行', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (180, 0, 'cp-code-redeemStat', '6', '银行已退单', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (181, 0, 'cp-code-redeemStat', '7', '重汇已提交', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (182, 0, 'cp-code-redeemStat', '8', '重汇已发送', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (183, 0, 'cp-code-redeemStat', '9', '重汇已退单', 'able', '2016-05-30 22:40:51', '2016-05-30 22:40:51');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (199, 0, 'cp-select-pay', '00', '处理完成或接收成功', 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (200, 0, 'cp-select-payStat', '1001', '处理完成或接收成功', 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (201, 0, 'cp-select-redeem', '0000', '提交成功', 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (202, 0, 'cp-select-redeemStat', 's', '交易成功', 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (203, 0, 'cp-select-verify', '000', '交易成功', 'able', '2016-05-31 09:21:08', '2016-05-31 09:21:08');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (204, 0, '', 'ip', '10.199.101.16', 'able', '2016-05-26 15:08:48', '2016-05-30 18:01:27');
INSERT INTO be.be_simulator_config (id, user_id, grp, k, val, status, created_at, updated_at)
VALUES (205, 0, '', 'port', '7080', 'able', '2016-05-26 15:08:48', '2016-05-30 18:01:27');

INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (16, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529190801.txt', 'redeem', 'able',
   '2016-05-29 19:08:01', '2016-05-29 19:08:30');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (17, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529190830.txt', 'pay', 'able',
   '2016-05-29 19:08:30', '2016-05-29 19:08:30');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (18, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529190831.txt', 'redeem', 'able',
   '2016-05-29 19:08:31', '2016-05-29 19:08:31');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (19, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529190900.txt', 'pay', 'able',
   '2016-05-29 19:09:00', '2016-05-29 19:09:00');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (20, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529190901.txt', 'redeem', 'able',
   '2016-05-29 19:09:01', '2016-05-29 19:09:01');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (21, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529190930.txt', 'pay', 'able',
   '2016-05-29 19:09:30', '2016-05-29 19:09:30');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (22, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529190931.txt', 'redeem', 'able',
   '2016-05-29 19:09:31', '2016-05-29 19:09:31');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (23, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529191000.txt', 'pay', 'able',
   '2016-05-29 19:10:00', '2016-05-29 19:10:00');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (24, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529191001.txt', 'redeem', 'able',
   '2016-05-29 19:10:01', '2016-05-29 19:10:01');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (25, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529191030.txt', 'pay', 'able',
   '2016-05-29 19:10:30', '2016-05-29 19:10:30');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (26, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529191031.txt', 'redeem', 'able',
   '2016-05-29 19:10:31', '2016-05-29 19:19:19');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (27, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529191100.txt', 'pay', 'able',
   '2016-05-29 19:11:00', '2016-05-29 19:11:00');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (28, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529200523.txt', 'pay', 'able',
   '2016-05-29 20:05:24', '2016-05-29 20:05:24');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (29, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529200539.txt', 'redeem', 'able',
   '2016-05-29 20:05:39', '2016-05-29 20:05:39');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (31, '999', 'download/dz/cp/20160529/808080211580085_20160529_20160529201725.txt', 'redeem', 'able',
   '2016-05-29 20:17:26', '2016-05-29 20:17:26');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (37, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530112951.txt', 'pay', 'able',
   '2016-05-30 11:29:52', '2016-05-30 11:29:52');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (38, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530112957.txt', 'redeem', 'able',
   '2016-05-30 11:29:57', '2016-05-30 11:29:57');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (39, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530133311.txt', 'pay', 'able',
   '2016-05-30 13:33:11', '2016-05-30 13:33:11');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (40, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530133344.txt', 'redeem', 'able',
   '2016-05-30 13:33:44', '2016-05-30 13:33:44');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (41, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530143247.txt', 'pay', 'able',
   '2016-05-30 14:32:48', '2016-05-30 14:32:48');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (42, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530180650.txt', 'pay', 'able',
   '2016-05-30 18:06:50', '2016-05-30 18:06:50');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (43, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530181010.txt', 'pay', 'able',
   '2016-05-30 18:10:10', '2016-05-30 18:10:10');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (44, '599', 'download/dz/ect/20160530/12398765432345_20160530_20160530183816.txt', 'verify', 'able',
   '2016-05-30 18:38:17', '2016-05-30 18:38:17');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (45, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530184615.txt', 'pay', 'able',
   '2016-05-30 18:46:15', '2016-05-30 18:46:15');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (47, '999', 'download/dz/cp/20160530/808080211580085_20160530_20160530184759.txt', 'pay', 'able',
   '2016-05-30 18:47:59', '2016-05-30 18:47:59');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (53, '999', 'download/dz/cp/20160531/808080211580085_20160531_20160531092333.txt.zip', 'redeem', 'able',
   '2016-05-31 09:23:34', '2016-05-31 09:23:34');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (54, '999', 'download/dz/cp/20160531/808080211580085_20160531_20160531092338.txt', 'pay', 'able',
   '2016-05-31 09:23:38', '2016-05-31 09:23:38');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (55, '999', 'download/dz/cp/20160531/808080211580085_20160531_20160531104838.txt', 'pay', 'able',
   '2016-05-31 10:48:38', '2016-05-31 10:48:38');
INSERT INTO be.be_simulator_dz (id, bank_no, path, trans_type, status, created_at, updated_at) VALUES
  (56, '999', 'download/dz/cp/20160531/808080211580085_20160531_20160531104857.txt.zip', 'redeem', 'able',
   '2016-05-31 10:48:57', '2016-05-31 10:48:57');

INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES
  (1, 'admin', '后台管理', 0, '/admin', 'able', 1, 'menu-icon fa fa-desktop', '2016-05-11 10:54:45', '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (2, 'dashboard', '工作台', 0, '/dashboard', 'able', 2, 'menu-icon fa fa-dashboard', '2016-05-11 10:54:45',
        '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES
  (11, 'admin-user', '用户', 1, '', 'able', 1, 'menu-icon fa fa-users', '2016-05-11 10:54:45', '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (29, 'dashboard-user', '个人', 2, '/dashboard/user', 'able', 9, 'menu-icon fa fa-user', '2016-05-11 10:54:45',
        '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES
  (111, 'admin-user-manage', '用户管理', 11, '/admin/user', 'able', 1, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES
  (112, 'admin-role-manage', '角色管理', 11, '/admin/role', 'able', 2, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES
  (113, 'admin-menu-manage', '菜单管理', 11, '/admin/menu', 'able', 3, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (291, 'dashboard-user-avatar', '修改头像', 29, '/dashboard/user/avatar', 'able', 1, '', '2016-05-11 10:54:45',
        '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (292, 'dashboard-user-profile', '修改资料', 29, '/dashboard/user/profile', 'able', 2, '', '2016-05-11 10:54:45',
        '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (293, 'dashboard-user-password', '修改密码', 29, '/dashboard/user/password', 'able', 3, '', '2016-05-11 10:54:45',
        '2016-05-11 10:54:47');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (294, 'dashboard-cp', '银联', 2, '/dashboard/cp', 'able', 1, 'menu-icon fa fa-list', '2016-05-26 14:17:59',
        '2016-05-26 14:23:30');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (295, 'dashboard-cp-code', '错误码配置', 294, '/dashboard/cp/code', 'able', 1, '', '2016-05-26 14:18:50',
        '2016-05-26 20:20:50');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (296, 'dashboard-cp-config', '模拟器配置', 294, '/dashboard/cp/config', 'able', 2, '', '2016-05-26 14:20:00',
        '2016-05-26 14:20:00');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (297, 'dashboard-cp-verify', '鉴权测试', 294, '/dashboard/cp/verify', 'able', 3, '', '2016-05-26 16:03:40',
        '2016-05-26 16:03:40');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (298, 'dashboard-cp-pay', '申购测试', 294, '/dashboard/cp/pay', 'able', 4, '', '2016-05-26 17:34:59',
        '2016-05-26 17:34:59');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (299, 'dashboard-cp-redeem', '赎回测试', 294, '/dashboard/cp/redeem', 'able', 5, '', '2016-05-26 18:40:35',
        '2016-05-26 18:40:35');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES
  (300, 'dashboard-transaction', '维护', 2, '/dashboard/trans', 'able', 8, 'menu-icon fa fa-bank', '2016-05-26 22:40:30',
   '2016-05-29 18:30:51');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES
  (302, 'dashboard-transaction-manage', '交易管理', 300, '/dashboard/transaction', 'able', 1, '', '2016-05-26 23:24:59',
   '2016-05-26 23:24:59');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (303, 'dashboard-cp-query', '代付单查测试', 294, '/dashboard/cp/query', 'able', 6, '', '2016-05-27 11:35:27',
        '2016-05-27 11:35:27');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (304, 'dashboard-cp-query2', '代扣单查测试', 294, '/dashboard/cp/query2', 'able', 7, '', '2016-05-27 11:53:39',
        '2016-05-27 11:53:39');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (305, 'admin-bank', '银行', 1, '/admin/bk', 'able', 2, 'menu-icon fa fa-list', '2016-05-29 16:19:32',
        '2016-05-29 16:19:52');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES
  (306, 'admin-bank-manage', '银行配置', 305, '/admin/bank', 'able', 1, '', '2016-05-29 16:20:24', '2016-05-29 16:20:24');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (307, 'dashboard-dz-manage', '对账文件管理', 300, '/dashboard/dz', 'able', 2, '', '2016-05-29 17:32:36',
        '2016-05-29 17:35:52');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (308, 'admin-sys', '系统', 1, '/admin/sys0', 'able', 3, 'menu-icon fa fa-cog', '2016-05-31 10:05:26',
        '2016-05-31 10:35:38');
INSERT INTO be.be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (310, 'admin-config-manage', '配置管理', 308, '/admin/config', 'able', 1, '', '2016-05-31 10:07:07',
        '2016-05-31 10:20:04');

INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 1);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 2);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 11);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 29);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 111);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 112);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 113);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 291);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 292);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 293);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 294);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 295);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 296);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 297);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 298);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 299);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 300);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 302);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 303);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 304);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 305);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 306);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 307);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 308);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 309);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (1, 310);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (2, 2);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (2, 29);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (2, 291);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (2, 292);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (2, 293);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (3, 294);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (3, 295);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (3, 296);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (3, 297);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (3, 298);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (3, 299);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (3, 303);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (3, 304);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (5, 300);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (5, 302);
INSERT INTO be.be_simulator_role_menu (role_id, menu_id) VALUES (5, 307);

INSERT INTO be.be_simulator_role (id, name, description, status, created_at, updated_at)
VALUES (1, 'ROLE_SYS_ADMIN', '系统管理员', 'able', '2016-05-11 10:47:44', '2016-05-11 10:47:56');
INSERT INTO be.be_simulator_role (id, name, description, status, created_at, updated_at)
VALUES (2, 'ROLE_USER', '普通用户', 'able', '2016-05-11 10:47:44', '2016-05-11 10:47:56');
INSERT INTO be.be_simulator_role (id, name, description, status, created_at, updated_at)
VALUES (3, 'ROLE_CP', '银联管理员', 'able', '2016-05-26 14:16:36', '2016-05-26 14:16:36');
INSERT INTO be.be_simulator_role (id, name, description, status, created_at, updated_at)
VALUES (4, 'ROLE_ECT', '证通管理员', 'able', '2016-05-30 17:11:35', '2016-05-30 17:11:35');
INSERT INTO be.be_simulator_role (id, name, description, status, created_at, updated_at)
VALUES (5, 'ROLE_BANK', '维护', 'able', '2016-05-30 17:12:21', '2016-05-30 17:12:21');

INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (1, '2016051900000752', '100056', '808080211580085', '999', '6226223300376434', 'redeem', '1001', '0000', 's', 'Y',
   '20160529', '', 'able', '20160528', '2016-05-28 14:25:34', '2016-05-29 14:15:49');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (2, '2016051900000727', '100057', '808080211580085', '999', '6212261717004591622', 'pay', '9901', '00', '1001', 'Y',
   '20160529', '', 'able', '20160528', '2016-05-28 14:25:42', '2016-05-29 14:15:43');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES (3, '2016051900000744', '100059', '808080211580085', '999', '6212261717004591622', 'pay', '9901', '00', 'S', 'I',
        '20160528', '', 'able', '20160529', '2016-05-29 15:35:38', '2016-05-29 17:08:11');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (4, '2016051900000123', '100060', '808080211580085', '999', '6212261717004591622', 'pay', '9901', '00', '1001', 'Y',
   '20160528', '', 'able', '20160529', '2016-05-29 15:37:22', '2016-05-29 17:08:08');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (5, '2016051900000711', '100061', '808080211580085', '999', '6212261717004591622', 'pay', '9901', '00', '1001', 'Y',
   '20160530', '', 'able', '20160530', '2016-05-30 11:29:15', '2016-05-30 11:29:15');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (6, '2016051900000111', '100063', '808080211580085', '999', '6212261717004591622', 'pay', '9901', '00', '1001', 'Y',
   '20160530', '', 'disable', '20160530', '2016-05-30 11:29:34', '2016-05-30 15:07:04');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (7, '2016051900001111', '100065', '808080211580085', '999', '6212261717004591622', 'redeem', '9901', '00', '1001',
      'Y', '20160530', '', 'able', '20160530', '2016-05-30 13:32:58', '2016-05-30 15:01:10');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (8, '2016051900001234', '100066', '808080211580085', '999', '6226223300376434', 'redeem', '1001', '0000', 's', 'Y',
   '20160530', '', 'disable', '20160530', '2016-05-30 13:33:28', '2016-05-30 15:07:02');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES (10, '2016022800000594', '100069', '808080211580085', '999', '6225233300002428', 'verify', '0', '000', '', 'Y',
        '20160518', '20160518160835', 'able', '20160530', '2016-05-30 14:54:10', '2016-05-30 14:54:10');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES (11, '2016033800000594', '100070', '808080211580085', '999', '6225233300002428', 'verify', '0', '000', '', 'Y',
        '20160518', '20160518160835', 'able', '20160530', '2016-05-30 14:54:15', '2016-05-30 14:54:15');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES (12, '2016033440000594', '100071', '808080211580085', '999', '6225233300002428', 'verify', '0', '000', '', 'Y',
        '20160518', '20160518160835', 'able', '20160530', '2016-05-30 14:54:20', '2016-05-30 14:54:20');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (13, '2016053000101341', '100072', '808080211580085', '999', '6212261717004592211', 'verify', '0', '000', '', 'Y',
   '20160530', '20160530150904', 'able', '20160530', '2016-05-30 15:09:15', '2016-05-30 15:09:15');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (14, '2016053000101342', '100073', '808080211580085', '999', '6212261717004592211', 'verify', '0', '000', '', 'Y',
   '20160530', '20160530151837', 'able', '20160530', '2016-05-30 15:18:00', '2016-05-30 15:18:00');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (15, '2016053000101343', '100074', '808080211580085', '999', '6212261717004592211', 'verify', '0', '000', '', 'Y',
   '20160530', '20160530152205', 'able', '20160530', '2016-05-30 15:21:27', '2016-05-30 15:21:27');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (16, '2016053000101344', '100075', '808080211580085', '999', '6212261717004592211', 'verify', '0', '003', '', 'I',
   '20160530', '20160530152453', 'able', '20160530', '2016-05-30 15:24:14', '2016-05-30 15:24:14');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (17, '2016061900000727', '100076', '808080211580085', '999', '6212261717004591622', 'pay', '9901', '00', '1001', 'Y',
   '20160530', '', 'able', '20160530', '2016-05-30 15:37:14', '2016-05-30 15:37:14');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (18, '2016053000101345', '100077', '808080211580085', '999', '6212261717004591622', 'pay', '4999999', '00', '1001',
       'Y', '20160530', '', 'able', '20160530', '2016-05-30 15:38:20', '2016-05-30 15:38:20');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (19, '2016053000101346', '100078', '808080211580085', '999', '6212261717004591622', 'pay', '4999999', '45', '2000',
       'I', '20160530', '', 'able', '20160530', '2016-05-30 15:40:02', '2016-05-30 15:40:02');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (20, '2016053000101347', '100079', '808080211580085', '999', '4367421214574378144', 'redeem', '101', '0000', 's', 'Y',
   '20160530', '', 'able', '20160530', '2016-05-30 15:50:21', '2016-05-30 15:50:21');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (21, '2016053000101348', '100080', '808080211580085', '999', '4367421214574378144', 'redeem', '101', '0000', 's', 'Y',
   '20160530', '', 'able', '20160530', '2016-05-30 15:53:04', '2016-05-30 15:53:04');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (22, '2016053000101349', '100081', '808080211580085', '999', '4367421214574378144', 'redeem', '101', '0103', '4', 'I',
   '20160530', '', 'able', '20160530', '2016-05-30 15:53:49', '2016-05-31 09:09:15');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (23, '2016053000101352', '100088', '808080211580085', '999', '6212261717004591622', 'pay', '4999999', '45', '2000',
       'I', '20160530', '', 'able', '20160530', '2016-05-30 17:59:33', '2016-05-30 17:59:33');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (24, '2016053100101356', '100096', '808080211580085', '999', '6212261717004592211', 'verify', '0', '003', '', 'I',
   '20160531', '20160531091247', 'able', '20160531', '2016-05-31 09:12:11', '2016-05-31 09:12:11');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (25, '2016053100101357', '100097', '808080211580085', '999', '6212261717004592211', 'verify', '0', '000', '', 'Y',
   '20160531', '20160531091351', 'able', '20160531', '2016-05-31 09:13:15', '2016-05-31 09:13:15');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (26, '2016053100101358', '100098', '808080211580085', '999', '6212261717004591622', 'pay', '4999999', '45', '2000',
       'I', '20160531', '', 'able', '20160531', '2016-05-31 09:16:27', '2016-05-31 09:16:27');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (27, '2016053100101359', '100099', '808080211580085', '999', '6212261717004591622', 'pay', '4999999', '00', '1001',
       'Y', '20160531', '', 'able', '20160531', '2016-05-31 09:17:52', '2016-05-31 09:17:52');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (28, '2016053100101360', '100100', '808080211580085', '999', '6212261717004591622', 'pay', '4999999', '00', '1001',
       'Y', '20160531', '', 'able', '20160531', '2016-05-31 09:20:07', '2016-05-31 09:20:07');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (29, '2016053100101361', '100101', '808080211580085', '999', '6212261717004591622', 'pay', '4999999', '00', '1001',
       'Y', '20160531', '', 'able', '20160531', '2016-05-31 09:20:31', '2016-05-31 09:20:31');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (30, '2016053100101362', '100102', '808080211580085', '999', '4367421214574378144', 'redeem', '101', '0103', '4', 'I',
   '20160531', '', 'able', '20160531', '2016-05-31 09:21:00', '2016-05-31 09:21:00');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (31, '2016053100101363', '100103', '808080211580085', '999', '4367421214574378144', 'redeem', '101', '0000', 's', 'Y',
   '20160531', '', 'able', '20160531', '2016-05-31 09:21:30', '2016-05-31 09:21:30');
INSERT INTO be.be_simulator_transaction (id, be_ser, bs_ser, mer_id, bank_no, acco_no, trans_type, amount, resp_code, trans_stat, stat, work_day, send_time, status, created_date, created_at, updated_at)
VALUES
  (32, '2016053100101368', '100109', '808080211580085', '999', '6212261717004591622', 'pay', '4999999', '00', '1001',
       'Y', '20160531', '', 'able', '20160531', '2016-05-31 10:24:55', '2016-05-31 10:24:55');

INSERT INTO be.be_simulator_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO be.be_simulator_user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO be.be_simulator_user_role (user_id, role_id) VALUES (2, 3);
INSERT INTO be.be_simulator_user_role (user_id, role_id) VALUES (2, 5);
INSERT INTO be.be_simulator_user_role (user_id, role_id) VALUES (3, 2);
INSERT INTO be.be_simulator_user_role (user_id, role_id) VALUES (3, 4);
INSERT INTO be.be_simulator_user_role (user_id, role_id) VALUES (3, 5);

INSERT INTO be.be_simulator_user (id, username, password, salt, realname, email, mobile, status, small_avatar, medium_avatar, large_avatar, created_at, updated_at)
VALUES (1, 'admin', '9606b0029ba4a8c9369f288cced0dc465eb5eabd', '3685072edcf8aad8', '系统管理员', 'kangyonggan@gmail.com',
           '18221372104', 'able', 'upload/41469070924597_s.jpg', 'upload/41469065546714_m.jpg',
           'upload/41468948397623_l.jpg', '2016-05-10 17:25:23', '2016-05-10 17:25:27');
INSERT INTO be.be_simulator_user (id, username, password, salt, realname, email, mobile, status, small_avatar, medium_avatar, large_avatar, created_at, updated_at)
VALUES
  (2, 'yinlian', '462fcdeaf7ef943464667e661b53eb5f04cc894d', 'd7bc1e268097c08c', '银联管理员', '', '', 'able', '', '', '',
   '2016-05-26 14:16:04', '2016-05-26 14:16:04');
INSERT INTO be.be_simulator_user (id, username, password, salt, realname, email, mobile, status, small_avatar, medium_avatar, large_avatar, created_at, updated_at)
VALUES
  (3, 'zhengtong', '36ed41382fbc864fef657a706dcf43546c9da202', '17ed9e91f5603c9a', '证通管理员', '', '', 'able', '', '', '',
   '2016-05-30 17:10:40', '2016-05-30 17:10:40');

