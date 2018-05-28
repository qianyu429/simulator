INSERT INTO be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (350, 'dashboard-citicb', '中信信用卡', 2, '/dashboard/citicb', 'able', 1, 'menu-icon fa fa-list', '2016-05-31 10:07:07',
        '2016-05-31 10:20:04');

INSERT INTO be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (351, 'dashboard-citicb-code', '错误码配置', 350, '/dashboard/citicb/code', 'able', 1, '', '2016-05-31 10:07:07',
        '2016-05-31 10:20:04');
INSERT INTO be_simulator_menu (id, name, description, pid, url, status, sort, icon, created_at, updated_at)
VALUES (352, 'dashboard-citicb-config', '模拟器配置', 350, '/dashboard/citicb/config', 'able', 1, '', '2016-05-31 10:07:07',
        '2016-05-31 10:20:04');

ALTER TABLE `be_simulator_transaction`
ADD COLUMN `protocolNo` VARCHAR(45) default NULL COMMENT '协议号' AFTER `updated_at`,
ADD COLUMN `memo1` VARCHAR(45) default NULL COMMENT '预留字段' AFTER `protocol`,
ADD COLUMN `memo2` VARCHAR(45) default NULL COMMENT '预留字段' AFTER `memo1`;