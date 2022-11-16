DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role`
(
    `id`           bigint                                                       NOT NULL COMMENT '主键',
    `role_name`    varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
    `state`        tinyint                                                      NULL DEFAULT NULL COMMENT '状态',
    `show_loc`     tinyint                                                      NULL DEFAULT NULL COMMENT '显示位置',
    `role_key`     varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '唯一标识',
    `is_universal` tinyint                                                      NULL DEFAULT NULL COMMENT '是否默认',
    `creater`      bigint                                                       NULL DEFAULT NULL COMMENT '创建人',
    `create_time`  datetime                                                     NULL DEFAULT NULL COMMENT '创建时间',
    `updater`      bigint                                                       NULL DEFAULT NULL COMMENT '修改人',
    `update_time`  datetime                                                     NULL DEFAULT NULL COMMENT '修改时间',
    `deleter`      bigint                                                       NULL DEFAULT NULL COMMENT '删除人',
    `delete_time`  datetime                                                     NULL DEFAULT NULL COMMENT '删除时间',
    `is_delete`    tinyint                                                      NULL DEFAULT 0 COMMENT '是否删除',
    `version`      int                                                          NULL DEFAULT NULL COMMENT '版本',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色'
  ROW_FORMAT = Dynamic;