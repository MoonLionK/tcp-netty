-- auto-generated definition
create table device
(
    id                 bigint(19) auto_increment comment 'id'
        primary key,
    protocol_id        bigint(19)                             not null comment '协议id',
    ip                 varchar(255)                           not null comment 'ip地址',
    port               int                                    not null comment '连接端口',
    device_name        varchar(255)                           not null comment '设备名称',
    device_type        varchar(255)                           null comment '设备类型',
    device_description varchar(255)                           null comment '设备描述',
    device_status      int          default 0                 not null comment '设备状态： 0:启用 1:禁用',
    tcp_model          varchar(255) default 'server'          null comment 'tcp通信模式,server || client',
    serial_port        varchar(255)                           null comment '串口号',
    baud_rate          varchar(255)                           null comment '波特率',
    creator            int                                    not null comment '创建人',
    create_time        datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater            int                                    not null comment '更新人',
    update_time        datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted            bit          default b'0'              not null comment '是否删除',
    remark             varchar(500)                           null comment '备注',
    constraint device_ip_port_uindex
        unique (ip, port)
)
    comment '设备信息表' charset = utf8;


# 初始化数据
INSERT INTO device (id, protocol_id, ip, port, device_name, device_type, device_description, device_status, tcp_model,
                    serial_port, baud_rate, creator, create_time, updater, update_time, deleted, remark)
VALUES (1, 1, '127.0.0.1', 8888, 'TCP设备1', 'TCP设备', null, 0, 'client', null, null, 1, '2024-04-30 15:17:21', 1,
        '2024-04-30 15:17:21', false, null);
INSERT INTO device (id, protocol_id, ip, port, device_name, device_type, device_description, device_status, tcp_model,
                    serial_port, baud_rate, creator, create_time, updater, update_time, deleted, remark)
VALUES (2, 1, '127.0.0.1', 7777, 'TCP设备2', 'TCP设备', null, 0, 'client', null, null, 1, '2024-04-30 15:17:21', 1,
        '2024-04-30 15:17:21', false, null);
INSERT INTO device (id, protocol_id, ip, port, device_name, device_type, device_description, device_status, tcp_model,
                    serial_port, baud_rate, creator, create_time, updater, update_time, deleted, remark)
VALUES (3, 1, '127.0.0.1', 6666, 'TCP设备3', 'TCP设备', null, 0, 'client', null, null, 1, '2024-04-30 15:17:21', 1,
        '2024-04-30 15:17:21', false, null);
INSERT INTO device (id, protocol_id, ip, port, device_name, device_type, device_description, device_status, tcp_model,
                    serial_port, baud_rate, creator, create_time, updater, update_time, deleted, remark)
VALUES (4, 1, '127.0.0.1', 7000, 'TCP设备1', 'TCP设备', null, 0, 'server', null, null, 1, '2024-04-30 15:17:21', 1,
        '2024-04-30 15:17:21', false, null);
INSERT INTO device (id, protocol_id, ip, port, device_name, device_type, device_description, device_status, tcp_model,
                    serial_port, baud_rate, creator, create_time, updater, update_time, deleted, remark)
VALUES (5, 1, '127.0.0.1', 8000, 'TCP设备2', 'TCP设备', null, 0, 'server', null, null, 1, '2024-04-30 15:17:21', 1,
        '2024-04-30 15:17:21', false, null);

