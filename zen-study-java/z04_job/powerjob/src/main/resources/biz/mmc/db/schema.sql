CREATE TABLE `task_model_instance`
(
    `id`                  int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
    `gmt_create`          datetime    NOT NULL COMMENT '创建时间',
    `gmt_modified`        datetime    NOT NULL COMMENT '修改时间',
    `is_deleted`          tinyint(4) NOT NULL COMMENT '0正常, 1删除',
    `tenant_id`           varchar(32) NOT NULL DEFAULT '' COMMENT '租户id',
    `creator`             varchar(32) NOT NULL COMMENT '创建者',
    `modifier`            varchar(32) NOT NULL COMMENT '修改者',
    `launcher`            varchar(32) NOT NULL DEFAULT '' COMMENT '触发人',
    `version`             int(11) NOT NULL DEFAULT '0' COMMENT '版本',
    `parent_id`           int(11) DEFAULT NULL COMMENT '父任务',
    `task_scene_code`     varchar(32) NOT NULL COMMENT '任务场景',
    `instance_name`       varchar(64) NOT NULL COMMENT '任务实例名称',
    `instance_indexes`    varchar(1024)        DEFAULT NULL COMMENT '任务实例索引',
    `feature`             varchar(4096)        DEFAULT NULL COMMENT '任务扩展字段',
    `process_status`      varchar(32) NOT NULL COMMENT '任务状态',
    `process_status_desc` varchar(64) NOT NULL COMMENT '任务状态描述',
    `env`                 varchar(32) NOT NULL DEFAULT '' COMMENT '环境标',
    `group_value`         varchar(64) NOT NULL DEFAULT '' COMMENT '组值',
    `order_id`            tinyint(4) NOT NULL DEFAULT '0' COMMENT '排序',
    `schedule_time`       datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '可以被调度的时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_task_name` (`task_scene_code`,`instance_name`,`env`)
) ENGINE=InnoDB AUTO_INCREMENT=1124 DEFAULT CHARSET=utf8mb4 COMMENT='任务实例表'
;

CREATE TABLE `task_model_instance_step`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
    `gmt_create`    datetime       NOT NULL COMMENT '创建时间',
    `gmt_modified`  datetime       NOT NULL COMMENT '修改时间',
    `is_deleted`    tinyint(4) NOT NULL COMMENT '0正常，1删除',
    `instance_id`   int(11) NOT NULL COMMENT '任务实例id',
    `env`           varchar(32)    NOT NULL COMMENT '环境标',
    `step_code`     varchar(32)    NOT NULL COMMENT '任务步骤code',
    `process_value` decimal(10, 2) NOT NULL COMMENT '任务步骤进度',
    `step_status`   varchar(32)    NOT NULL COMMENT '任务步骤状态',
    `priority`      tinyint(4) NOT NULL DEFAULT '0' COMMENT '优先级',
    PRIMARY KEY (`id`),
    KEY             `idx_instance_id` (`instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET =utf8mb4 COMMENT='任务步骤表'
;

CREATE TABLE `task_model_instance_result`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
    `gmt_create`   datetime    NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime    NOT NULL COMMENT '修改时间',
    `is_deleted`   tinyint(4) NOT NULL COMMENT '0正常，1删除',
    `instance_id`  int(11) NOT NULL COMMENT '任务实例id',
    `version`      int(11) NOT NULL DEFAULT '0' COMMENT '版本',
    `result_type`  varchar(32) NOT NULL COMMENT '结果类型',
    `content`      mediumtext  NOT NULL COMMENT '结果内容',
    PRIMARY KEY (`id`),
    KEY            `idx_instance_id` (`instance_id`),
    KEY            `idx_instance_version_type`(`instance_id`,`version`,`result_type`)
) ENGINE=InnoDB DEFAULT CHARSET =utf8mb4 COMMENT='任务结果表'
;

CREATE TABLE `task_model_sys_config`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
    `gmt_create`   datetime      NOT NULL COMMENT '创建时间',
    `gmt_modified` datetime      NOT NULL COMMENT '修改时间',
    `env`          varchar(32)   NOT NULL COMMENT '环境标',
    `type`         varchar(64)   NOT NULL COMMENT '配置类型',
    `config_code`  varchar(64)   NOT NULL COMMENT 'code',
    `config_value` varchar(1028) NOT NULL COMMENT 'value',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_index`(`env`,`type`,`config_code`)
) ENGINE=InnoDB DEFAULT CHARSET =utf8mb4 COMMENT='任务结果表'
;

