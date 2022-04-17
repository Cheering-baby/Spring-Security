CREATE DATABASE spring_security;
USE spring_security;

-- ----------------------------
-- Table structure for ums_admin
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `username` varchar(64) DEFAULT NULL,
                             `password` varchar(64) DEFAULT NULL,
                             `icon` varchar(500) DEFAULT NULL COMMENT '头像',
                             `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                             `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
                             `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
                             `status` int(1) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- password: admin
INSERT INTO `ums_admin` VALUES ('1', 'admin', '$2a$10$EqOtvC23Bx2Fp862ZvaiHukixUkQft8hQoOMPIUMHHvUOqKXmS1VO', null, null, '测试账号', null, NOW(), NOW(), '1');

-- ----------------------------
-- Table structure for ums_permission
-- ----------------------------
DROP TABLE IF EXISTS `ums_permission`;
CREATE TABLE `ums_permission` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `pid` bigint(20) DEFAULT NULL COMMENT '父级权限id',
                                  `name` varchar(100) DEFAULT NULL COMMENT '名称',
                                  `value` varchar(200) DEFAULT NULL COMMENT '权限值',
                                  `icon` varchar(500) DEFAULT NULL COMMENT '图标',
                                  `type` int(1) DEFAULT NULL COMMENT '权限类型：0->目录；1->菜单；2->按钮（接口绑定权限）',
                                  `uri` varchar(200) DEFAULT NULL COMMENT '前端资源路径',
                                  `status` int(1) DEFAULT NULL COMMENT '启用状态；0->禁用；1->启用',
                                  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                  `sort` int(11) DEFAULT NULL COMMENT '排序',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='后台用户权限表';

-- ----------------------------
-- Records of ums_permission
-- ----------------------------
INSERT INTO `ums_permission` VALUES ('1', '0', '商品', null, null, '0', null, '1', NOW(), '0');
INSERT INTO `ums_permission` VALUES ('2', '1', '商品列表', 'pms:product:read', null, '1', '/pms/product/index', '1', NOW(), '0');
INSERT INTO `ums_permission` VALUES ('3', '1', '添加商品', 'pms:product:create', null, '1', '/pms/product/add', '1', NOW(), '0');
INSERT INTO `ums_permission` VALUES ('4', '1', '商品分类', 'pms:productCategory:read', null, '1', '/pms/productCate/index', '1', NOW(), '0');
INSERT INTO `ums_permission` VALUES ('5', '1', '商品类型', 'pms:productAttribute:read', null, '1', '/pms/productAttr/index', '1', NOW(), '0');
INSERT INTO `ums_permission` VALUES ('6', '0', '通过username查询用户', 'ums:query:username', null, '1', '/user//queryUserByUsername', '1', NOW(), '0');

-- ----------------------------
-- Table structure for ums_admin_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_permission_relation`;
CREATE TABLE `ums_admin_permission_relation` (
                                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                                 `admin_id` bigint(20) DEFAULT NULL,
                                                 `permission_id` bigint(20) DEFAULT NULL,
                                                 `type` int(1) DEFAULT NULL,
                                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台用户和权限关系表(除角色中定义的权限以外的加减权限)';

-- ----------------------------
-- Records of ums_admin_permission_relation
-- ----------------------------
INSERT INTO `ums_admin_permission_relation` VALUES ("1", "1", "2", NULL);
INSERT INTO `ums_admin_permission_relation` VALUES ("2", "1", "6", NULL);