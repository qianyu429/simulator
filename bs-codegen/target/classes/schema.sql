CREATE DATABASE `bs`
  DEFAULT CHARACTER SET utf8
  COLLATE utf8_general_ci;

USE bs;

CREATE TABLE `config` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  `user_id`      BIGINT       NOT NULL
  COMMENT '配置所属的用户',
  `grp`          VARCHAR(32)  NULL     DEFAULT ''
  COMMENT '组',
  `k`            VARCHAR(32)  NULL     DEFAULT ''
  COMMENT '键',
  `val`          VARCHAR(255) NOT NULL
  COMMENT '值',
  `status`       VARCHAR(32)  NOT NULL DEFAULT 'able'
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  `created_time` DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_time` DATETIME     NOT NULL
  COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC)
)
  COMMENT '配置表';


CREATE TABLE `user` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  `username`     VARCHAR(64)  NOT NULL
  COMMENT '用户名',
  `password`     VARCHAR(64)  NOT NULL
  COMMENT '密码',
  `salt`         VARCHAR(128) NOT NULL
  COMMENT '密码盐',
  `realname`     VARCHAR(32)  NULL     DEFAULT ''
  COMMENT '真实姓名',
  `email`        VARCHAR(64)  NULL     DEFAULT ''
  COMMENT '电子邮箱',
  `mobile`       VARCHAR(20)  NULL     DEFAULT ''
  COMMENT '手机号',
  `status`       VARCHAR(32)  NOT NULL DEFAULT 'able'
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  `small_avatar`  VARCHAR(255) NULL     DEFAULT ''
  COMMENT '小头像',
  `medium_avatar` VARCHAR(255) NULL     DEFAULT ''
  COMMENT '中头像',
  `large_avatar`  VARCHAR(255) NULL     DEFAULT ''
  COMMENT '大头像',
  `created_time` DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_time` DATETIME     NOT NULL
  COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC)
)
  COMMENT '用户表';

CREATE TABLE `role` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  `name`         VARCHAR(64)  NOT NULL
  COMMENT '角色名称',
  `description`  VARCHAR(128) NOT NULL
  COMMENT '角色描述',
  `status`       VARCHAR(32)  NOT NULL DEFAULT 'able'
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  `created_time` DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_time` DATETIME     NOT NULL
  COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
)
  COMMENT '角色表';

CREATE TABLE `menu` (
  `id`           BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键, 自增',
  `name`         VARCHAR(64)  NOT NULL
  COMMENT '菜单名称',
  `description`  VARCHAR(128) NOT NULL
  COMMENT '菜单描述',
  `pid`          BIGINT       NOT NULL
  COMMENT '父菜单ID',
  `url`          VARCHAR(128) NOT NULL
  COMMENT '菜单URL',
  `status`       VARCHAR(32)  NOT NULL DEFAULT 'able'
  COMMENT '是否禁用, able:已启用, disable:已禁用',
  `sort`         INT          NOT NULL
  COMMENT '菜单排序',
  `icon`         VARCHAR(128) NULL     DEFAULT ''
  COMMENT '菜单图标的class',
  `created_time` DATETIME     NOT NULL
  COMMENT '创建时间',
  `updated_time` DATETIME     NOT NULL
  COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  UNIQUE INDEX `url_UNIQUE` (`url` ASC)
)
  COMMENT '菜单表';

CREATE TABLE `user_role` (
  `user_id` BIGINT NOT NULL
  COMMENT '用户ID',
  `role_id` BIGINT NOT NULL
  COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`)
)
  COMMENT '用户角色表';

CREATE TABLE `role_menu` (
  `role_id` BIGINT NOT NULL
  COMMENT '角色ID',
  `menu_id` BIGINT NOT NULL
  COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`)
)
  COMMENT '角色菜单表';


ALTER TABLE be_simulator_bank MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE be_simulator_config MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE be_simulator_dz MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE be_simulator_menu MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE be_simulator_role MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE be_simulator_transaction MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY;
ALTER TABLE be_simulator_user MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY;

ALTER TABLE be_simulator_user_role
  ADD CONSTRAINT PK_USER_ROLE_PRIMARY PRIMARY KEY(user_id, role_id);

ALTER TABLE be_simulator_role_menu
  ADD CONSTRAINT PK_ROLE_MENU_PRIMARY PRIMARY KEY(menu_id, role_id);
