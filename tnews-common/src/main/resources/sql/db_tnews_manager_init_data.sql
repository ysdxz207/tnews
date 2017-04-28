

-- ----------------------------
-- Records of manager_menu
-- ----------------------------
INSERT INTO `manager_menu` VALUES ('1', '新闻管理', null, '1', null, '1', '新闻管理', '2', 'news_manage', '0', '0');
INSERT INTO `manager_menu` VALUES ('2', '权限管理', null, '2', null, '1', '权限管理', '1', 'role_manage', '0', '0');
INSERT INTO `manager_menu` VALUES ('3', '新闻信息', null, '3', 'news/false', '1', '新闻信息', '2', 'news', '1', '0');
INSERT INTO `manager_menu` VALUES ('4', '用户信息', null, '4', 'user/false', '1', '用户信息', '1', 'user', '2', '0');
INSERT INTO `manager_menu` VALUES ('5', '角色信息', null, '5', 'role/false', '1', '角色信息', '1', 'role', '2', '0');
INSERT INTO `manager_menu` VALUES ('6', '菜单信息', null, '6', 'menu/false', '1', '菜单信息', '1', 'menu', '2', '0');
INSERT INTO `manager_menu` VALUES ('7', '权限信息', null, '7', 'permission/false', '1', '权限信息', '1', 'permission', '2', '0');
INSERT INTO `manager_menu` VALUES ('8', '新闻频道', null, '8', 'news/channel/false', '1', '新闻频道', '2', 'news_channel', '1', '0');
INSERT INTO `manager_menu` VALUES ('9', '字典管理', null, '9', null, '1', '字典管理', '3', 'dictionary_manage', '0', '0');
INSERT INTO `manager_menu` VALUES ('10', '字典信息', null, '10', 'dictionary/false', '1', '字典信息', '3', 'dictionary', '9', '0');


-- ----------------------------
-- Records of manager_permission
-- ----------------------------
INSERT INTO `manager_permission` VALUES ('1', '4', '查看用户', 'user:view');
INSERT INTO `manager_permission` VALUES ('2', '4', '添加用户', 'user:add');
INSERT INTO `manager_permission` VALUES ('3', '4', '编辑用户', 'user:edit');
INSERT INTO `manager_permission` VALUES ('4', '4', '删除用户', 'user:delete');
INSERT INTO `manager_permission` VALUES ('5', '5', '查看角色', 'role:view');
INSERT INTO `manager_permission` VALUES ('6', '5', '添加角色', 'role:add');
INSERT INTO `manager_permission` VALUES ('7', '5', '编辑角色', 'role:edit');
INSERT INTO `manager_permission` VALUES ('8', '5', '删除角色', 'role:delete');
INSERT INTO `manager_permission` VALUES ('9', '5', '设置权限', 'role:setPermission');
INSERT INTO `manager_permission` VALUES ('10', '6', '查看菜单', 'menu:view');
INSERT INTO `manager_permission` VALUES ('11', '6', '添加菜单', 'menu:add');
INSERT INTO `manager_permission` VALUES ('12', '6', '编辑菜单', 'menu:edit');
INSERT INTO `manager_permission` VALUES ('13', '6', '删除菜单', 'menu:delete');
INSERT INTO `manager_permission` VALUES ('14', '7', '查看权限', 'permission:view');
INSERT INTO `manager_permission` VALUES ('15', '7', '添加权限', 'permission:add');
INSERT INTO `manager_permission` VALUES ('16', '7', '编辑权限', 'permission:edit');
INSERT INTO `manager_permission` VALUES ('17', '7', '删除权限', 'permission:delete');
INSERT INTO `manager_permission` VALUES ('18', '3', '查看新闻', 'news:view');
INSERT INTO `manager_permission` VALUES ('19', '3', '添加新闻', 'news:add');
INSERT INTO `manager_permission` VALUES ('20', '3', '编辑新闻', 'news:edit');
INSERT INTO `manager_permission` VALUES ('21', '3', '删除新闻', 'news:delete');
INSERT INTO `manager_permission` VALUES ('22', '8', '查看新闻频道', 'news:channel:view');
INSERT INTO `manager_permission` VALUES ('23', '8', '添加新闻频道', 'news:channel:add');
INSERT INTO `manager_permission` VALUES ('24', '8', '编辑新闻频道', 'news:channel:edit');
INSERT INTO `manager_permission` VALUES ('25', '8', '删除新闻频道', 'news:channel:delete');
INSERT INTO `manager_permission` VALUES ('26', '10', '查看字典', 'dictionary:view');
INSERT INTO `manager_permission` VALUES ('27', '10', '添加字典', 'dictionary:add');
INSERT INTO `manager_permission` VALUES ('28', '10', '编辑字典', 'dictionary:edit');
INSERT INTO `manager_permission` VALUES ('29', '10', '删除字典', 'dictionary:delete');



-- ----------------------------
-- Records of manager_role
-- ----------------------------
INSERT INTO `manager_role` VALUES ('1', '管理员', 'admin');



-- ----------------------------
-- Records of manager_role_permission
-- ----------------------------
INSERT INTO `manager_role_permission` VALUES ('1', '1', '1');
INSERT INTO `manager_role_permission` VALUES ('2', '1', '2');
INSERT INTO `manager_role_permission` VALUES ('3', '1', '3');
INSERT INTO `manager_role_permission` VALUES ('4', '1', '4');
INSERT INTO `manager_role_permission` VALUES ('5', '1', '5');
INSERT INTO `manager_role_permission` VALUES ('6', '1', '6');
INSERT INTO `manager_role_permission` VALUES ('7', '1', '7');
INSERT INTO `manager_role_permission` VALUES ('8', '1', '8');
INSERT INTO `manager_role_permission` VALUES ('9', '1', '9');
INSERT INTO `manager_role_permission` VALUES ('10', '1', '10');
INSERT INTO `manager_role_permission` VALUES ('11', '1', '11');
INSERT INTO `manager_role_permission` VALUES ('12', '1', '12');
INSERT INTO `manager_role_permission` VALUES ('13', '1', '13');
INSERT INTO `manager_role_permission` VALUES ('14', '1', '14');
INSERT INTO `manager_role_permission` VALUES ('15', '1', '15');
INSERT INTO `manager_role_permission` VALUES ('16', '1', '16');
INSERT INTO `manager_role_permission` VALUES ('17', '1', '17');
INSERT INTO `manager_role_permission` VALUES ('18', '1', '18');
INSERT INTO `manager_role_permission` VALUES ('19', '1', '19');
INSERT INTO `manager_role_permission` VALUES ('20', '1', '20');
INSERT INTO `manager_role_permission` VALUES ('21', '1', '21');
INSERT INTO `manager_role_permission` VALUES ('22', '1', '22');
INSERT INTO `manager_role_permission` VALUES ('23', '1', '23');
INSERT INTO `manager_role_permission` VALUES ('24', '1', '24');
INSERT INTO `manager_role_permission` VALUES ('25', '1', '25');
INSERT INTO `manager_role_permission` VALUES ('26', '1', '26');
INSERT INTO `manager_role_permission` VALUES ('27', '1', '27');
INSERT INTO `manager_role_permission` VALUES ('28', '1', '28');
INSERT INTO `manager_role_permission` VALUES ('29', '1', '29');



-- ----------------------------
-- Records of manager_user
-- ----------------------------
INSERT INTO `manager_user` VALUES ('1', 'admin', '超级管理员', 'E10ADC3949BA59ABBE56E057F20F883E', '2016-12-05 10:07:37', '2017-01-04 09:31:47', '1');
INSERT INTO `manager_user` VALUES ('2', 'huangfeihong', '黄飞鸿', 'E10ADC3949BA59ABBE56E057F20F883E', '2016-12-05 10:07:37', '2016-12-26 17:34:34', '1');


-- ----------------------------
-- Records of manager_user_role
-- ----------------------------
INSERT INTO `manager_user_role` VALUES ('1', '1', '1');
INSERT INTO `manager_user_role` VALUES ('2', '2', '1');
