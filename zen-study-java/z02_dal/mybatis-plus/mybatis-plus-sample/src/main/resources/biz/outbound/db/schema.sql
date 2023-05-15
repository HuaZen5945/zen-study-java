-- 工作日表
DROP TABLE IF EXISTS `working_day`;
CREATE TABLE `working_day` (
       `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据id',
       `gmt_create` datetime NOT NULL COMMENT '创建时间',
       `gmt_modified` datetime NOT NULL COMMENT '修改时间',
       `ds` int(11) NOT NULL COMMENT '日期',
       `type` int(11) NOT NULL COMMENT '1 工作日，2非工作日',
       PRIMARY KEY (`id`),
       UNIQUE KEY  (`ds`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作日表'
;