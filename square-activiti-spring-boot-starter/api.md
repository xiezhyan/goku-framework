# square-activiti-spring-boot-starter

## 流程操作


---
### 跳转到可视化界面

#### BASIC

**Path：** /model/rest/creator

**Method：** GET

#### REQUEST


**Query：**

| name  |  value  |  required | desc  |
| ------------ | ------------ | ------------ | ------------ |
| modelId |  | NO | 流程编号 |
| tenantId |  | NO | 租户 |
| category |  | NO | 分类 |



---
### 分页获取流程数据

#### BASIC

**Path：** /model/rest/list

**Method：** GET

#### REQUEST


**Query：**

| name  |  value  |  required | desc  |
| ------------ | ------------ | ------------ | ------------ |
| name |  | NO | 流程名称 |
| key |  | NO | 唯一标识 |
| category |  | NO | 分类 |
| tenantId |  | NO | 租户 |
| currentIndex | 1 | NO | 页码 |
| pageSize | 10 | NO | 显示条数 |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | object |  | 
| &ensp;&ensp;&#124;─pagination | object | 分页参数 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─pageSize | integer | 当前显示的条数,默认显示100条 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─currentIndex | integer | 当前显示的页数 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─sorts | array | 排序 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─field | string |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─sorted | string |  | 
| &ensp;&ensp;&ensp;&ensp;&#124;─totalCount | integer | 查询总数 | 
| &ensp;&ensp;&#124;─datas | array | 结果集 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─id | string | 编号 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─name | string | 名称 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─key | string | 唯一标识 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─category | string | 分类 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─createTime | string | 创建时间 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─lastUpdateTime | string | 最后一次修改时间 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─metaInfo | string | 元数据信息 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─deploymentId | string | 部署ID | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─version | integer | 版本号 | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": {
    "pagination": {
      "pageSize": 0,
      "currentIndex": 0,
      "sorts": [
        {
          "field": "",
          "sorted": ""
        }
      ],
      "totalCount": 0
    },
    "datas": [
      {
        "id": "",
        "name": "",
        "key": "",
        "category": "",
        "createTime": "",
        "lastUpdateTime": "",
        "metaInfo": "",
        "deploymentId": "",
        "version": 0
      }
    ]
  }
}
```



---
### 发布流程

#### BASIC

**Path：** /model/rest/deploy/{modelId}

**Method：** POST

#### REQUEST


**Path Params：**

| name  |  value   | desc  |
| ------------ | ------------ | ------------ |
| modelId |  | 流程ID |

**Headers：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | boolean |  | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": false
}
```



---
### 删除流程

#### BASIC

**Path：** /model/rest/{modelId}

**Method：** DELETE

#### REQUEST


**Path Params：**

| name  |  value   | desc  |
| ------------ | ------------ | ------------ |
| modelId |  | 流程ID |

**Headers：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | boolean |  | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": false
}
```



---
### 通过 processDefinitionId 获取当前Model

#### BASIC

**Path：** /model/rest/{processDefinitionId}

**Method：** GET

#### REQUEST


**Path Params：**

| name  |  value   | desc  |
| ------------ | ------------ | ------------ |
| processDefinitionId |  | processDefinitionId |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | object |  | 
| &ensp;&ensp;&#124;─id | string | 编号 | 
| &ensp;&ensp;&#124;─name | string | 名称 | 
| &ensp;&ensp;&#124;─key | string | 唯一标识 | 
| &ensp;&ensp;&#124;─category | string | 分类 | 
| &ensp;&ensp;&#124;─createTime | string | 创建时间 | 
| &ensp;&ensp;&#124;─lastUpdateTime | string | 最后一次修改时间 | 
| &ensp;&ensp;&#124;─metaInfo | string | 元数据信息 | 
| &ensp;&ensp;&#124;─deploymentId | string | 部署ID | 
| &ensp;&ensp;&#124;─version | integer | 版本号 | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": {
    "id": "",
    "name": "",
    "key": "",
    "category": "",
    "createTime": "",
    "lastUpdateTime": "",
    "metaInfo": "",
    "deploymentId": "",
    "version": 0
  }
}
```



---
### 查看当前流程图

#### BASIC

**Path：** /model/rest/viewPic

**Method：** GET

#### REQUEST


**Query：**

| name  |  value  |  required | desc  |
| ------------ | ------------ | ------------ | ------------ |
| processDefinitionKey |  | YES | 流程定义key |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | string |  | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": ""
}
```




## 实例操作


---
### 启动流程

#### BASIC

**Path：** /business/start

**Method：** POST

#### REQUEST


**Headers：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**RequestBody**

| name | type | desc |
| ------------ | ------------ | ------------ |
| processDefinitionKey | string | 流程图唯一标识 | 
| businessKey | string | 业务关联键 | 
| variables | object | 参数 | 
| &ensp;&ensp;&#124;─key | object |  | 
| assgnee | string | 代理人:[设置代理人接口必填] | 

**Request Demo：**

```json
{
  "processDefinitionKey": "",
  "businessKey": "",
  "variables": {
    "": {}
  },
  "assgnee": ""
}
```


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | boolean |  | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": false
}
```



---
### 设置代理人

#### BASIC

**Path：** /business/assgnee

**Method：** POST

#### REQUEST


**Headers：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**RequestBody**

| name | type | desc |
| ------------ | ------------ | ------------ |
| processDefinitionKey | string | 流程图唯一标识 | 
| businessKey | string | 业务关联键 | 
| variables | object | 参数 | 
| &ensp;&ensp;&#124;─key | object |  | 
| assgnee | string | 代理人:[设置代理人接口必填] | 

**Request Demo：**

```json
{
  "processDefinitionKey": "",
  "businessKey": "",
  "variables": {
    "": {}
  },
  "assgnee": ""
}
```


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | boolean |  | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": false
}
```



---
### 查看评论列表

#### BASIC

**Path：** /business/comment

**Method：** GET

#### REQUEST


**Query：**

| name  |  value  |  required | desc  |
| ------------ | ------------ | ------------ | ------------ |
| businessKey |  | YES | 业务关联键 |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | array |  | 
| &ensp;&ensp;&#124;─ | object |  | 
| &ensp;&ensp;&ensp;&ensp;&#124;─id | string | 编号 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─userId | string | 用户ID | 
| &ensp;&ensp;&ensp;&ensp;&#124;─time | string | 评论时间 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─taskId | string | 实例ID | 
| &ensp;&ensp;&ensp;&ensp;&#124;─processInstanceId | string | 引用做出此评论的流程实例 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─type | string | 对注释类型的引用 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─fullMessage | string | 完整评论消息 | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": [
    {
      "id": "",
      "userId": "",
      "time": "",
      "taskId": "",
      "processInstanceId": "",
      "type": "",
      "fullMessage": ""
    }
  ]
}
```



---
### 提交任务

#### BASIC

**Path：** /business/completeByBusinessKey

**Method：** POST

#### REQUEST


**Headers：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**RequestBody**

| name | type | desc |
| ------------ | ------------ | ------------ |
| processDefinitionKey | string | 流程图唯一标识 | 
| businessKey | string | 业务关联键 | 
| variables | object | 参数 | 
| &ensp;&ensp;&#124;─key | object |  | 
| assgnee | string | 代理人:[设置代理人接口必填] | 

**Request Demo：**

```json
{
  "processDefinitionKey": "",
  "businessKey": "",
  "variables": {
    "": {}
  },
  "assgnee": ""
}
```


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | object |  | 
| &ensp;&ensp;&#124;─taskId | string | 实例ID | 
| &ensp;&ensp;&#124;─isOk | boolean | 是否提交成功 | 
| &ensp;&ensp;&#124;─isFinished | boolean | 流程是否结束 | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": {
    "taskId": "",
    "isOk": false,
    "isFinished": false
  }
}
```



---
### 待办任务

#### BASIC

**Path：** /business/getTaskListByAssignee

**Method：** GET

#### REQUEST


**Query：**

| name  |  value  |  required | desc  |
| ------------ | ------------ | ------------ | ------------ |
| businessKey |  | YES | 业务关联键 |
| userId |  | YES | 当前用户 |
| isActive | true | NO | 是否是活动状态 |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | array |  | 
| &ensp;&ensp;&#124;─ | object |  | 
| &ensp;&ensp;&ensp;&ensp;&#124;─assignee | string | 拥有者 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─taskId | string | 任务ID | 
| &ensp;&ensp;&ensp;&ensp;&#124;─taskName | string | 任务名称 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─processDefinitionId | string | 流程定义ID | 
| &ensp;&ensp;&ensp;&ensp;&#124;─processInstanceId | string | 流程实例ID | 
| &ensp;&ensp;&ensp;&ensp;&#124;─executionId | string | 执行对象ID | 
| &ensp;&ensp;&ensp;&ensp;&#124;─createTime | string | 任务创办时间 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─processVariables | object | 流程参数 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─key | object |  | 
| &ensp;&ensp;&ensp;&ensp;&#124;─taskLocalVariables | object | 当前任务参数 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─key | object |  | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": [
    {
      "assignee": "",
      "taskId": "",
      "taskName": "",
      "processDefinitionId": "",
      "processInstanceId": "",
      "executionId": "",
      "createTime": "",
      "processVariables": {
        "": {}
      },
      "taskLocalVariables": {
        "": {}
      }
    }
  ]
}
```



---
### 撤销流程

#### BASIC

**Path：** /business/revokeFlow

**Method：** DELETE

#### REQUEST


**Headers：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/json | YES |  |

**RequestBody**

| name | type | desc |
| ------------ | ------------ | ------------ |
| processDefinitionKey | string | 流程图唯一标识 | 
| businessKey | string | 业务关联键 | 
| userId | string | 用户ID | 
| reason | string | 撤销原因 | 

**Request Demo：**

```json
{
  "processDefinitionKey": "",
  "businessKey": "",
  "userId": "",
  "reason": ""
}
```


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | boolean |  | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": false
}
```



---
### 历史流程

#### BASIC

**Path：** /business/getHistoryTaskList

**Method：** GET

#### REQUEST


**Query：**

| name  |  value  |  required | desc  |
| ------------ | ------------ | ------------ | ------------ |
| processDefinitionKey |  | NO | 流程唯一标识 |
| businessKey |  | NO | 业务关联键 |
| userId |  | NO | 当前用户 |
| currentIndex | 1 | NO | 页码 |
| pageSize | 10 | NO | 当前展示条数 |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  | 
| &ensp;&ensp;&#124;─success | boolean |  | 
| &ensp;&ensp;&#124;─message | string |  | 
| &ensp;&ensp;&#124;─code | integer |  | 
| data | object |  | 
| &ensp;&ensp;&#124;─pagination | object | 分页参数 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─pageSize | integer | 当前显示的条数,默认显示100条 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─currentIndex | integer | 当前显示的页数 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─sorts | array | 排序 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─field | string |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─sorted | string |  | 
| &ensp;&ensp;&ensp;&ensp;&#124;─totalCount | integer | 查询总数 | 
| &ensp;&ensp;&#124;─datas | array | 结果集 | 
| &ensp;&ensp;&ensp;&ensp;&#124;─ | object |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─startTime | string | 开始时间 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─endTime | string | 结束时间 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─claimTime | string | 领取任务的时间 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─assignee | string | 拥有者 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─taskId | string | 任务ID | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─taskName | string | 任务名称 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─processDefinitionId | string | 流程定义ID | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─processInstanceId | string | 流程实例ID | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─executionId | string | 执行对象ID | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─createTime | string | 任务创办时间 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─processVariables | object | 流程参数 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─key | object |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─taskLocalVariables | object | 当前任务参数 | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─key | object |  | 

**Response Demo：**

```json
{
  "meta": {
    "success": false,
    "message": "",
    "code": 0
  },
  "data": {
    "pagination": {
      "pageSize": 0,
      "currentIndex": 0,
      "sorts": [
        {
          "field": "",
          "sorted": ""
        }
      ],
      "totalCount": 0
    },
    "datas": [
      {
        "startTime": "",
        "endTime": "",
        "claimTime": "",
        "assignee": "",
        "taskId": "",
        "taskName": "",
        "processDefinitionId": "",
        "processInstanceId": "",
        "executionId": "",
        "createTime": "",
        "processVariables": {
          "": {}
        },
        "taskLocalVariables": {
          "": {}
        }
      }
    ]
  }
}
```




## 系统自带


---
### 保存流程

#### BASIC

**Path：** /activiti/model/{modelId}/save

**Method：** PUT

#### REQUEST


**Path Params：**

| name  |  value   | desc  |
| ------------ | ------------ | ------------ |
| modelId |  | 流程ID |

**Headers：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |

**Query：**

| name  |  value  |  required | desc  |
| ------------ | ------------ | ------------ | ------------ |
| name |  | NO | 流程名称 |
| description |  | NO | 描述 |
| json_xml |  | NO | 详细数据 |
| svg_xml |  | NO | 详细数据 |



---
### getEditorJson

#### BASIC

**Path：** /activiti/model/{modelId}/json

**Method：** GET

#### REQUEST


**Path Params：**

| name  |  value   | desc  |
| ------------ | ------------ | ------------ |
| modelId |  |  |


#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
| _children | object | Note: LinkedHashMap for backwards compatibility | 
| &ensp;&ensp;&#124;─key | array |  | 
| &ensp;&ensp;&ensp;&ensp;&#124;─ | array |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | array |  | 
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  | 
| _nodeFactory | object | We will keep a reference to the Object (usually TreeMapper)<br>that can construct instances of nodes to add to this container<br>node. | 
| &ensp;&ensp;&#124;─_cfgBigDecimalExact | boolean |  | 

**Response Demo：**

```json
{
  "_children": {
    "": [
      [
        []
      ]
    ]
  },
  "_nodeFactory": {
    "_cfgBigDecimalExact": false
  }
}
```



---
### getStencilset

#### BASIC

**Path：** /activiti/editor/stencilset

**Method：** GET

#### REQUEST



#### RESPONSE

**Header：**

| name  |  value  |  required  | desc  |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |   |

**Body：**

| name | type | desc |
| ------------ | ------------ | ------------ |
|  | string |  | 

**Response Demo：**

```json

```




