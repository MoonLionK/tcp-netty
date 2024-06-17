#     ********所有新建的表都必须包含这5个字段***********
#     create_time   DATETIME     not null DEFAULT CURRENT_TIMESTAMP comment '创建时间',
#     update_time   DATETIME     not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
#     creator       int          not null comment '创建人',
#     updater       int          not null comment '更新人',
#     deleted bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',

DROP DATABASE IF EXISTS `data`;

CREATE DATABASE `data` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

USE `data`;

create table if not exists project_info
(
    id           bigint(19) auto_increment comment 'id'
        primary key,
    project_name varchar(64)                        not null comment '项目名称',
    project_code varchar(64)                        not null comment '项目编码',
    description  varchar(255)                       null comment '描述',
    creator      int                                not null comment '创建人',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      int                                not null comment '更新人',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit      default b'0'              not null comment '是否删除',
    constraint project_code_uindex
        unique (project_code)
)
    comment '项目信息表' charset = utf8;

create table if not exists protocol
(
    id                   bigint(19) auto_increment comment 'id'
        primary key,
    app_id               varchar(64)                        not null comment '应用ID',
    protocol_name        varchar(64)                        null comment '协议名称',
    protocol_code        varchar(64)                        not null comment '协议编码',
    protocol_type        varchar(64)                        not null comment '通讯协议类型 ：mqtt ||tcp || http ||串口(serial)',
    protocol_detail_type varchar(64)                        null comment '产品协议详细类型 ：水文协议 ||北京地灾协议',
    status               tinyint  default 0                 not null comment '状态(字典值：0启用  1停用)',
    creator              int                                not null comment '创建人',
    create_time          datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updater              int                                not null comment '更新人',
    update_time          datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted              bit      default b'0'              not null comment '是否删除',
    remark               varchar(512)                       null comment '备注',
    constraint protocol_code_uindex
        unique (protocol_code)
)
    comment '协议信息表' charset = utf8;

create table if not exists device
(
    id                 bigint(19) auto_increment comment 'id'
        primary key,
    protocol_code      varchar(64)                            not null comment '协议编码',
    project_code       varchar(64)                            not null comment '项目编码',
    channel_code       varchar(64)                            not null comment '通道编码',
    device_name        varchar(64)                            not null comment '设备名称',
    device_code        varchar(64)                            not null comment '设备编码',
    device_type        varchar(64)                            not null comment '设备类型(根据不同的设备类型选择不同的解析和计算方式)',
    connect_type       int                                    null comment '连接方式：0：Tcp，1：Serial，2：MQTT',
    collect_type       int                                    null comment '报文采集方式：0：不采集；1：自动间隔采集；2：阈值采集，暂时不做',
    device_description varchar(255)                           null comment '设备描述',
    device_status      int          default 0                 not null comment '设备状态： 0:启用 1:禁用',
    tcp_model          varchar(255) default 'server'          null comment 'tcp通信模式,server || client',
    creator            int                                    not null comment '创建人',
    create_time        datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater            int                                    not null comment '更新人',
    update_time        datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted            bit          default b'0'              not null comment '是否删除',
    remark             varchar(500)                           null comment '备注',
    constraint device_code_uindex
        unique (device_code)
)
    comment '设备信息表' charset = utf8;

create table if not exists packet_info
(
    `id`                 bigint(19) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `device_code`        varchar(64)         DEFAULT NULL COMMENT '设备编码',
    `type`               int(11)             DEFAULT NULL COMMENT '拆包方式的类型：1.固定包长度模式 2.包头+包尾模式 3.不定长长度的包头位置的模式',
    `head`               binary(6)           DEFAULT NULL COMMENT '包头的标识符',
    `tail_first`         binary(6)           DEFAULT NULL COMMENT '包尾的标识符-1',
    `tail_second`        binary(6)           DEFAULT NULL COMMENT '包尾的标识符-2',
    `tail_third`         binary(6)           DEFAULT NULL COMMENT '包尾的标识符-3',
    `offset`             int(11)             DEFAULT NULL COMMENT '包头的忽略的字节偏移量，从0开始',
    `length_offset`      int(11)             DEFAULT NULL COMMENT '长度位对应的开始下标，从0开始',
    `length`             int(11)             DEFAULT NULL COMMENT '包的长度',
    `end_length`         int(11)             DEFAULT NULL COMMENT '包尾后字节数',
    `head_packet_length` int(11)             DEFAULT NULL COMMENT '长度位的长度',
    `add_length`         int(11)             DEFAULT NULL COMMENT '需要添加的长度',
    `method_type`        int(11)             DEFAULT NULL COMMENT '字节转换类型',
    `create_time`        datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `creator`            int(11)    NOT NULL COMMENT '创建人',
    `updater`            int(11)    NOT NULL COMMENT '更新人',
    `deleted`            bit(1)     NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) comment '设备拆包方法表' charset = utf8;

create table if not exists channel_info
(
    id               bigint(19) auto_increment comment 'id'
        primary key,
    ip               varchar(64)                        not null comment 'ip地址',
    port             int                                not null comment '连接端口',
    channel_code     varchar(64)                        not null comment '通道编码',
    serial_port      varchar(64)                        null comment '串口号',
    serial_rate      varchar(64)                        null comment '串口-波特率',
    serial_data_bits varchar(64)                        null comment '串口-数据位',
    serial_stop_bits varchar(46)                        null comment '串口-停止位',
    creator          int                                not null comment '创建人',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          int                                not null comment '更新人',
    update_time      datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted          bit      default b'0'              not null comment '是否删除',
    constraint channel_code_uindex
        unique (channel_code)
)
    comment '通道信息表' charset = utf8;

create table if not exists connect_info
(
    id          bigint(19) auto_increment comment 'id'
        primary key,
    ip          varchar(255)                       not null comment 'ip地址',
    remote_port int                                not null comment '远程端口',
    channel_id  bigint(19)                         not null comment '通道id',
    creator     int                                not null comment '创建人',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     int                                not null comment '更新人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit      default b'0'              not null comment '是否删除'
)
    comment '连接信息表' charset = utf8;


create table if not exists sensor_info
(
    id          bigint(19) auto_increment comment 'id'
        primary key,
    device_code varchar(64)                        not null comment '设备编码',
    sensor_name varchar(64)                        not null comment '传感器名称',
    sensor_code varchar(64)                        not null comment '传感器编码',
    creator     int                                not null comment '创建人',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     int                                not null comment '更新人',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit      default b'0'              not null comment '是否删除',
    constraint sensor_code_uindex
        unique (sensor_code)
) comment '传感器信息表' charset = utf8;

create table if not exists sensor_match_template
(
    `id`                  bigint(19)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `protocol_group_name` varchar(64)  NOT NULL COMMENT '协议分组名称',
    `device_group_name`   varchar(64)  NOT NULL COMMENT '设备分组名称',
    `sensor_group_name`   varchar(64)  NOT NULL COMMENT '传感器分组名称',
    `key`                 varchar(64)  NOT NULL COMMENT '匹配的key值',
    `value`               varchar(64)  NULL COMMENT '匹配的value值',
    `check_type`          int(1)       NOT NULL COMMENT '匹配方式,0为key+value，1为key匹配,2为value包含',
    `logic_type`          int(1)       NOT NULL default 0 COMMENT '逻辑规则：0:必须满足 1:满足其中一个即可',
    `regular`             varchar(128) NULL COMMENT '值校验的正则表达式',
    `remark`              varchar(512) null comment '备注',
    `creator`             int(11)      NOT NULL COMMENT '创建人',
    `create_time`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`             int(11)      NOT NULL COMMENT '更新人',
    `update_time`         datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`             bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    constraint name_key_uindex
        unique (protocol_group_name,device_group_name,sensor_group_name, `key`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='匹配规则模版表';

create table if not exists sensor_match_config
(
    `id`          bigint(19)  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sensor_code` varchar(64) NOT NULL COMMENT '传感器code',
    `match_rule`  varchar(64) NOT NULL COMMENT '匹配规则',
    `check_type`  int(1)      NOT NULL COMMENT '匹配方式,0为key+value，1为key匹配,2为value包含',
    `logic_type`  int(1)      NOT NULL default 0 COMMENT '逻辑规则：0:必须满足 1:满足其中一个即可',
    `creator`     int(11)     NOT NULL COMMENT '创建人',
    `create_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`     int(11)     NOT NULL COMMENT '更新人',
    `update_time` datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`     bit(1)      NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='传感器匹配配置表';

create table if not exists sensor_calculate_config
(
    `id`                bigint(19)   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `sensor_code` varchar(64) NOT NULL COMMENT '传感器code',
    `calculate_rule`    varchar(255) NOT NULL COMMENT '计算规则',
    `calculate_type`    int(1)       NOT NULL COMMENT '计算方式,0为公式计算，1为直接取值',
    `calculate_param`   json         NULL COMMENT '常量计算参数',
    `result_name`       varchar(64)  NOT NULL COMMENT '计算结果名称',
    `other_sensor_code` varchar(64)  null comment '计算时需要关联查询的其他传感器code',
    `time_range`        varchar(64)  null comment '时间范围',
    `creator`           int(11)      NOT NULL COMMENT '创建人',
    `create_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`           int(11)      NOT NULL COMMENT '更新人',
    `update_time`       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`           bit(1)       NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='传感器计算公式表';

create table if not exists sensor_calculate_template
(
    `id`             bigint(19)  NOT NULL AUTO_INCREMENT COMMENT 'id',
    `protocol_group_name` varchar(64)  NOT NULL COMMENT '协议分组名称',
    `device_group_name`   varchar(64)  NOT NULL COMMENT '设备分组名称',
    `sensor_group_name`   varchar(64)  NOT NULL COMMENT '传感器分组名称',
    `calculate_rule`    varchar(255) NOT NULL COMMENT '计算规则',
    `calculate_type`    int(1)       NOT NULL COMMENT '计算方式,0为公式计算，1为直接取值',
    `calculate_param`   json         NULL COMMENT '常量计算参数',
    `result_name`       varchar(36)  NOT NULL COMMENT '计算结果名称',
    `other_sensor_code` varchar(64)  null comment '计算时需要关联查询的其他传感器code',
    `time_range`        varchar(64)  null comment '时间范围',
    `creator`        int(11)     NOT NULL COMMENT '创建人',
    `create_time`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater`        int(11)     NOT NULL COMMENT '更新人',
    `update_time`    datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`        bit(1)      NOT NULL DEFAULT b'0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='传感器计算公式模版表';

create table if not exists matched_data
(
    id           bigint(19) auto_increment comment 'id'
        primary key,
    sensor_code  varchar(64)                        not null comment '传感器code',
    matched_data json                               null comment '匹配成功的数据',
    creator      int                                not null comment '创建人',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      int                                not null comment '更新人',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit      default b'0'              not null comment '是否删除'
) comment '匹配成功数据记录表' charset = utf8;

create table if not exists calculated_data
(
    id              bigint(19) auto_increment comment 'id'
        primary key,
    sensor_code     varchar(64)                        not null comment '传感器code',
    calculated_data json                               null comment '计算后的数据',
    creator         int                                not null comment '创建人',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         int                                not null comment '更新人',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         bit      default b'0'              not null comment '是否删除'
) comment '计算后数据记录表' charset = utf8;

create table if not exists transform_config
(
    id             bigint(19) auto_increment comment 'id'
        primary key,
    sensor_id      bigint(19)                         not null comment '传感器id',
    ip             varchar(255)                       not null comment 'ip地址',
    port           int                                not null comment '连接端口',
    platform_name  varchar(255)                       not null comment '平台名称',
    transform_rule varchar(255)                       not null comment '转发规则',
    status         int      default 0                 not null comment '状态： 0:启用 1:禁用',
    creator        int                                not null comment '创建人',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updater        int                                not null comment '更新人',
    update_time    datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted        bit      default b'0'              not null comment '是否删除',
    remark         varchar(500)                       null comment '备注',
    constraint transform_ip_port_uindex
        unique (ip, port)
) comment '转发配置表' charset = utf8;











