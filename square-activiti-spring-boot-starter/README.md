# Activiti审批

# 操作
## 新建流程图
```http request
GET http://localhost:8080/activiti/creator
```

## 获取流程图列表
```http request
GET http://localhost:8080/activiti/list
```

## 查看流程图
```http request
GET http://localhost:8080/modeler.html?modelId={modelId}
```

## 发布
```http request
POST http://localhost:8080/activiti/deploy/{modelId}
```
## 查看当前进度


## 保存
```http request
PUT http://localhost:8080/activiti/model/{modelId}/save
```

# 数据表
```mysql
-- 1 当部署流程文件时 影响4张表

-- 系统属性表-存有next.dbid
select * from  act_re_procdef ;
-- 资源表，相当于附近表
select * from act_ge_bytearray;
-- 部署对象表
select * from act_re_deployment ;
-- 部署对象表
select * from act_re_procdef ;


-- 2 启动流程 影响7张表

-- 执行对象表
select * from act_ru_execution;
-- 流程实例历史表
select* from act_hi_procinst;
-- 当前正在执行任务表----待办列表
select* from act_ru_task;
-- 历史任务表
select* from act_hi_taskinst;
-- 历史活动表
select * from act_hi_actinst;
-- 当前任务执行人表
select * from act_ru_identitylink;
-- 历史任务执行人表
select* from act_hi_identitylink;

-- 3 使用流程变量 影响2张表

-- 运行时流程变量表
select * from act_ru_variable;
-- 历史流程变量表
select * from act_hi_varinst;



```