USE bs;

INSERT INTO user
  (id, username, password, salt, realname, status, created_time, updated_time)
VALUES
  (1, 'admin', '9606b0029ba4a8c9369f288cced0dc465eb5eabd', '3685072edcf8aad8', '系统管理员', 'able', '2016-05-10 17:25:23', '2016-05-10 17:25:27');

INSERT INTO role
  (id, name, description, status, created_time, updated_time)
VALUES
  (1, 'ROLE_SYS_ADMIN', '系统管理员', 'able', '2016-05-11 10:47:44', '2016-05-11 10:47:56'),
  (2, 'ROLE_USER', '普通用户', 'able', '2016-05-11 10:47:44', '2016-05-11 10:47:56');

INSERT INTO menu
(id, name, description, pid, url, status, sort, icon, created_time, updated_time)
VALUES
  (1, 'admin', '后台管理', 0, '/admin', 'able', 1, 'menu-icon fa fa-desktop', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),
  (2, 'dashboard', '工作台', 0, '/dashboard', 'able', 2, 'menu-icon fa fa-dashboard', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),

  (11, 'admin-user', '用户', 1, '', 'able', 1, 'menu-icon fa fa-users', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),

  (111, 'admin-user-manage', '用户管理', 11, '/admin/user', 'able', 1, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),
  (112, 'admin-role-manage', '角色管理', 11, '/admin/role', 'able', 2, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),
  (113, 'admin-menu-manage', '菜单管理', 11, '/admin/menu', 'able', 3, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),

  (29, 'dashboard-user', '个人', 2, '/dashboard/user', 'able', 9, 'menu-icon fa fa-user', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),

  (291, 'dashboard-user-avatar', '修改头像', 29, '/dashboard/user/avatar', 'able', 1, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),
  (292, 'dashboard-user-profile', '修改资料', 29, '/dashboard/user/profile', 'able', 2, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47'),
  (293, 'dashboard-user-password', '修改密码', 29, '/dashboard/user/password', 'able', 3, '', '2016-05-11 10:54:45', '2016-05-11 10:54:47');

INSERT INTO user_role
  (user_id, role_id)
VALUES
  (1, 1);

INSERT INTO role_menu (role_id, menu_id) SELECT 1, id FROM menu;

INSERT INTO role_menu
  (role_id, menu_id)
VALUES
  (2, 2),
  (2, 29),
  (2, 291),
  (2, 292),
  (2, 293);

INSERT INTO config
  (`user_id`, `grp`, `k`, `val`, `status`, `created_time`, `updated_time`)
VALUES
  # 系统配置 如皮肤
  ('0', 'skin', 'no-skin', '#438EB9', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('0', 'sys', 'navbar', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('0', 'sys', 'sidebar', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('0', 'sys', 'breadcrumbs', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('0', 'sys', 'container', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('0', 'sys', 'hover', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('0', 'sys', 'compact', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('0', 'sys', 'highlight', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('1', 'skin', 'no-skin', '#438EB9', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('1', 'sys', 'navbar', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('1', 'sys', 'sidebar', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('1', 'sys', 'breadcrumbs', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('1', 'sys', 'container', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('1', 'sys', 'hover', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('1', 'sys', 'compact', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06'),
  ('1', 'sys', 'highlight', '0', 'able', '2016-05-18 18:52:06', '2016-05-18 18:52:06');
