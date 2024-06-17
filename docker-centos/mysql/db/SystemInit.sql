#     ********所有新建的表都必须包含这5个字段***********
#     create_time   DATETIME     not null DEFAULT CURRENT_TIMESTAMP comment '创建时间',
#     update_time   DATETIME     not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
#     creator       int          not null comment '创建人',
#     updater       int          not null comment '更新人',
#     deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',

DROP DATABASE IF EXISTS `hertzbeat`;

create database hertzbeat default charset utf8mb4 collate utf8mb4_general_ci;

DROP DATABASE IF EXISTS `system`;

CREATE DATABASE `system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `system`;

DROP TABLE IF EXISTS `system_authorization`;
CREATE TABLE system_authorization
(
    id            int auto_increment comment '编号'
        primary key,
    type          smallint(1)  null comment '注册模式 1:机器码',
    app_code      varchar(255) null comment '申请码',
    reg_code      varchar(255) null comment '注册码',
    device_num    int          null comment '设备数',
    validity_time date         null comment '有效期',
    function      varchar(255) null comment '功能',
    status        smallint(1)  null comment '状态(0未注册,1:注册成功)',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    creator       int          not null comment '创建人',
    updater       int          not null comment '更新人',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    remark        varchar(64)  null comment '备注'
)
    comment '系统注册授权表' collate = utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `system_user`;
CREATE TABLE system_user
(
    id            int auto_increment comment '主键id'
        primary key,
    nickname      varchar(50)  null comment '昵称',
    username      varchar(20)  not null comment '用户名',
    password      varchar(128) not null comment '密码',
    org_id        int          not null comment '机构',
    org_full_id   varchar(20)  null comment '机构全量id',
    sex           varchar(2)   null comment '性别',
    mobile_phone  varchar(20)  null comment '手机',
    code          varchar(50)  null comment '区号',
    telephone     varchar(20)  null comment '电话',
    email         varchar(50)  null comment '邮箱',
    department    varchar(128) null comment '部门',
    job           varchar(128) null comment '职务',
    status        smallint(1)  null comment '数据状态（是否禁用，0禁用，1未禁用）',
    voice_status  int(1)                default 1 null comment '是否开启声音 1：是 0：否 ',
    `create_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    creator       int          not null comment '创建人',
    updater       int          not null comment '更新人',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    remark        varchar(64)  null comment '备注'
)
    comment '用户表' collate = utf8mb4_unicode_ci;

-- auto-generated definition
DROP TABLE IF EXISTS `system_operate_log`;
CREATE TABLE `system_operate_log`
(
    `id`             bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `trace_id`       varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL DEFAULT '' COMMENT '链路追踪编号',
    `user_id`        bigint                                                         NOT NULL COMMENT '用户编号',
    `user_type`      tinyint                                                        NOT NULL DEFAULT 0 COMMENT '用户类型',
    `type`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '操作模块类型',
    `sub_type`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NOT NULL COMMENT '操作名',
    `biz_id`         bigint                                                         NOT NULL COMMENT '操作数据模块编号',
    `action`         varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作内容',
    `extra`          varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '拓展字段',
    `request_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NULL     DEFAULT '' COMMENT '请求方法名',
    `request_url`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '请求地址',
    `user_ip`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NULL     DEFAULT NULL COMMENT '用户 IP',
    `user_agent`     varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT NULL COMMENT '浏览器 UA',
    `creator`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NULL     DEFAULT '' COMMENT '创建者',
    `create_time`    datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci   NULL     DEFAULT '' COMMENT '更新者',
    `update_time`    datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)                                                         NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id`      bigint                                                         NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '操作日志记录 V2 版本';

DROP TABLE IF EXISTS `system_dict_type`;
CREATE TABLE `system_dict_type`
(
    `id`           bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `name`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典名称',
    `type`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
    `status`       tinyint                                                       NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `remark`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '备注',
    `creator`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '创建者',
    `create_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`      varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '更新者',
    `update_time`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`      bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `deleted_time` datetime                                                      NULL     DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `dict_type` (`type` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '字典类型表';


DROP TABLE IF EXISTS `system_dict_data`;
CREATE TABLE `system_dict_data`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `sort`        int                                                           NOT NULL DEFAULT 0 COMMENT '字典排序',
    `label`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典标签',
    `value`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '字典类型',
    `status`      tinyint                                                       NOT NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
    `color_type`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT '' COMMENT '颜色类型',
    `css_class`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT '' COMMENT 'css 样式',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '字典数据表';
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (1, 0, '未处理', '0', 'system_api_error_log_process_status', 0, 'primary', '', NULL, '', '2021-02-26 07:07:19',
        '1', '2022-02-16 20:14:17', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (2, 1, '已处理', '1', 'system_api_error_log_process_status', 0, 'success', '', NULL, '', '2021-02-26 07:07:26',
        '1', '2022-02-16 20:14:08', b'0');
INSERT INTO `system_dict_data` (`id`, `sort`, `label`, `value`, `dict_type`, `status`, `color_type`, `css_class`,
                                `remark`, `creator`, `create_time`, `updater`, `update_time`, `deleted`)
VALUES (3, 2, '已忽略', '2', 'system_api_error_log_process_status', 0, 'danger', '', NULL, '', '2021-02-26 07:07:34',
        '1', '2022-02-16 20:14:14', b'0');



DROP TABLE IF EXISTS `system_api_error_log`;
CREATE TABLE `system_api_error_log`
(
    `id`                           int                                                          NOT NULL AUTO_INCREMENT COMMENT '编号',
    `trace_id`                     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '链路追踪编号',
    `user_id`                      int                                                          NOT NULL DEFAULT 0 COMMENT '用户编号',
    `user_type`                    tinyint                                                      NOT NULL DEFAULT 0 COMMENT '用户类型',
    `application_name`             varchar(50)                                                  NOT NULL COMMENT '应用名',
    `request_method`               varchar(16)                                                  NOT NULL COMMENT '请求方法名',
    `request_url`                  varchar(255)                                                 NOT NULL COMMENT '请求地址',
    `request_params`               varchar(8000)                                                NOT NULL COMMENT '请求参数',
    `user_ip`                      varchar(50)                                                  NOT NULL COMMENT '用户 IP',
    `user_agent`                   varchar(512)                                                 NOT NULL COMMENT '浏览器 UA',
    `exception_time`               datetime                                                     NOT NULL COMMENT '异常发生时间',
    `exception_name`               varchar(128)                                                 NOT NULL DEFAULT '' COMMENT '异常名',
    `exception_message`            text                                                         NOT NULL COMMENT '异常导致的消息',
    `exception_root_cause_message` text                                                         NOT NULL COMMENT '异常导致的根消息',
    `exception_stack_trace`        text                                                         NOT NULL COMMENT '异常的栈轨迹',
    `exception_class_name`         varchar(512)                                                 NOT NULL COMMENT '异常发生的类全名',
    `exception_file_name`          varchar(512)                                                 NOT NULL COMMENT '异常发生的类文件',
    `exception_method_name`        varchar(512)                                                 NOT NULL COMMENT '异常发生的方法名',
    `exception_line_number`        int                                                          NOT NULL COMMENT '异常发生的方法所在行',
    `process_status`               tinyint                                                      NOT NULL default 0 COMMENT '处理状态 0:未处理 1:已处理 2:已忽略',
    `process_time`                 datetime                                                     NULL     DEFAULT NULL COMMENT '处理时间',
    `process_user_id`              int                                                          NULL     DEFAULT 0 COMMENT '处理用户编号',
    `creator`                      varchar(64)                                                  NULL     DEFAULT '' COMMENT '创建者',
    `create_time`                  datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`                      varchar(64)                                                  NULL     DEFAULT '' COMMENT '更新者',
    `update_time`                  datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`                      bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '系统异常日志';

DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config`
(
    `id`          int                                                           NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    `category`    varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '参数分组',
    `type`        tinyint                                                       NOT NULL COMMENT '参数类型',
    `name`        varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数名称',
    `config_key`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键名',
    `value`       varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '参数键值',
    `visible`     bit(1)                                                        NOT NULL COMMENT '是否可见',
    `remark`      varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '备注',
    `creator`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '创建者',
    `create_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '更新者',
    `update_time` datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '参数配置表';





