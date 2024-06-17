# DML语句,测试数据
INSERT INTO `data`.`calculate_config` (`id`, `calculate_code`, `calculate_rule`, `calculate_type`, `calculate_param`, `result_name`, `other_sensor_code`, `time_range`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, 'CA00001', 'P58/ρ+init', 0, '{\"ρ\": 9.8, \"init\": 10}', '渗透水压力01', NULL, NULL, 1, '2024-05-29 17:31:36', 1, '2024-05-30 10:46:07', b'0');

INSERT INTO `data`.`channel_info` (`id`, `ip`, `port`, `channel_code`, `serial_port`, `serial_rate`, `serial_data_bits`, `serial_stop_bits`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, '127.0.0.1', 6000, 'CC00001', NULL, NULL, NULL, NULL, 1, '2024-05-21 19:27:48', 1, '2024-05-30 11:17:54', b'0');
INSERT INTO `data`.`channel_info` (`id`, `ip`, `port`, `channel_code`, `serial_port`, `serial_rate`, `serial_data_bits`, `serial_stop_bits`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (3, '127.0.0.1', 6597, 'DC00002', NULL, NULL, NULL, NULL, 1, '2024-05-21 19:27:48', 1, '2024-05-30 10:58:33', b'0');

INSERT INTO `data`.`connect_info` (`id`, `ip`, `remote_port`, `channel_id`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, '127.0.0.1', 5007, 3, 1, '2024-05-29 17:36:01', 1, '2024-05-29 17:36:01', b'0');

INSERT INTO `data`.`device` (`id`, `protocol_code`, `project_code`, `channel_code`, `device_name`, `device_code`, `device_type`, `connect_type`, `collect_type`, `device_description`, `device_status`, `tcp_model`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `remark`) VALUES (1, 'PT01', 'PJ01', 'CC00001', '水文协议大坝安全设备', 'DC00001', '水文协议大坝安全设备', 0, 1, NULL, 0, 'server', 1, '2024-05-21 19:24:19', 1, '2024-05-30 11:18:02', b'0', NULL);
INSERT INTO `data`.`device` (`id`, `protocol_code`, `project_code`, `channel_code`, `device_name`, `device_code`, `device_type`, `connect_type`, `collect_type`, `device_description`, `device_status`, `tcp_model`, `creator`, `create_time`, `updater`, `update_time`, `deleted`, `remark`) VALUES (2, 'PT02', 'PJ02', 'DC00002', '六通道振弦', 'DC00002', '六通道振弦', 0, 1, NULL, 0, 'server', 1, '2024-05-21 19:24:19', 1, '2024-05-30 10:26:38', b'0', NULL);

INSERT INTO `data`.`packet_info` (`id`, `device_code`, `type`, `head`, `tail_first`, `tail_second`, `tail_third`, `offset`, `length_offset`, `length`, `end_length`, `head_packet_length`, `add_length`, `method_type`, `create_time`, `update_time`, `creator`, `updater`, `deleted`) VALUES (1, 'DC00001', 3, 0x7E7E00000000, NULL, NULL, NULL, 0, 11, 0, 0, 2, 17, 2, '2024-05-15 11:40:24', '2024-05-30 10:44:48', 1, 1, b'0');

INSERT INTO `data`.`project_info` (`id`, `project_name`, `project_code`, `description`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, '测试项目1', 'PJ00001', '测试1', 1, '2024-05-21 19:21:31', 1, '2024-05-30 10:45:13', b'0');

INSERT INTO `data`.`sensor_info` (`id`, `device_code`, `sensor_name`, `sensor_code`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, 'DC00001', '渗透水压力01', 'DC00001-001', 1, '2024-05-29 17:33:02', 1, '2024-05-30 10:19:50', b'0');

INSERT INTO `data`.`sensor_match_config` (`id`, `sensor_code`, `match_rule`, `check_type`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, 'DC00001-001', 'P1:00608F5451', 0, 1, '2024-05-29 17:15:55', 1, '2024-05-30 10:46:32', b'0');
INSERT INTO `data`.`sensor_match_config` (`id`, `sensor_code`, `match_rule`, `check_type`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (2, 'DC00001-001', 'P10:75', 0, 1, '2024-05-29 17:16:45', 1, '2024-05-30 11:13:41', b'0');
INSERT INTO `data`.`sensor_match_config` (`id`, `sensor_code`, `match_rule`, `check_type`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (3, 'DC00001-001', 'P5A', 1, 1, '2024-05-29 17:24:15', 1, '2024-05-30 10:46:32', b'0');
INSERT INTO `data`.`sensor_match_config` (`id`, `sensor_code`, `match_rule`, `check_type`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (4, 'DC00001-001', 'PFF02', 1, 1, '2024-05-29 17:24:58', 1, '2024-05-30 10:46:32', b'0');

INSERT INTO `data`.`sensor_calculate_ref` (`id`, `calculate_code`, `sensor_code`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, 'CA00001', 'DC00001-001', 1, '2024-05-30 11:53:10', 1, '2024-05-30 11:53:10', b'0');

INSERT INTO `data`.`sensor_info` (`id`, `device_code`, `sensor_name`, `sensor_code`, `creator`, `create_time`, `updater`, `update_time`, `deleted`) VALUES (1, 'DC00001', '渗透压1', 'DC00001-001', 1, '2024-05-30 14:46:00', 1, '2024-05-30 14:46:00', b'0');
