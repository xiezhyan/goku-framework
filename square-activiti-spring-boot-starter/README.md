# Activiti审批

# 操作
## 新建流程图
```http request
GET http://localhost:8080/model/rest/creator?modelId={modelId}&tenantId={tenantId}&category={category}
```

## 获取流程图列表
```http request
GET http://localhost:8080/model/rest/list?name={name}&key={key}&category={category}&tenantId={tenantId}
```

## 发布
```http request
POST http://localhost:8080/model/rest/deploy/{modelId}
```
## 删除流程
```http request
DELETE http://localhost:8080/model/rest/{modelId}
```

# 数据表
```mysql
-- 1 当部署流程文件时 影响4张表

-- 系统属性表-存有NEXT.DBID
SELECT * FROM  ACT_GE_PROPERTY ;
-- 资源表，相当于附近表
SELECT * FROM ACT_GE_BYTEARRAY;
-- 流程存储表
SELECT * FROM ACT_RE_MODEL;
-- 部署对象表
SELECT * FROM ACT_RE_DEPLOYMENT ;
-- 部署对象表
SELECT * FROM ACT_RE_PROCDEF ;


-- 2 启动流程 影响7张表

-- 执行对象表
SELECT * FROM ACT_RU_EXECUTION;
-- 流程实例历史表
SELECT* FROM ACT_HI_PROCINST;
-- 当前正在执行任务表----待办列表
SELECT* FROM ACT_RU_TASK;
-- 历史任务表
SELECT* FROM ACT_HI_TASKINST;
-- 历史活动表
SELECT * FROM ACT_HI_ACTINST;
-- 当前任务执行人表
SELECT * FROM ACT_RU_IDENTITYLINK;
-- 历史任务执行人表
SELECT* FROM ACT_HI_IDENTITYLINK;

-- 3 使用流程变量 影响2张表

-- 运行时流程变量表
SELECT * FROM ACT_RU_VARIABLE;
-- 历史流程变量表
SELECT * FROM ACT_HI_VARINST;



```