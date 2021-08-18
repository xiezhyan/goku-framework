
# 实例操作
## 启动流程
**URL:** `/business/start`

**Type:** `POST`

**Author:** mr.sanq

**Content-Type:** `application/json; charset=utf-8`

**Description:** 启动流程




**Body-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
processDefinitionKey|string|流程图唯一标识|true|-
businessKey|string|业务关联键|true|-
variables|map|参数|true|-
└─any object|object|any object.|false|-
assgnee|string|代理人:[设置代理人接口必填]|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i /business/start --data '{
  "processDefinitionKey": "83drvs",
  "businessKey": "jk2iye",
  "variables": {
    "mapKey": {}
  },
  "assgnee": "9no55s"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
meta|object|No comments found.|-
└─success|boolean|No comments found.|-
└─message|string|No comments found.|-
└─code|int32|No comments found.|-
data|object|No comments found.|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 519
  },
  "data": true
}
```

## 设置代理人
**URL:** `/business/assgnee`

**Type:** `POST`

**Author:** mr.sanq

**Content-Type:** `application/json; charset=utf-8`

**Description:** 设置代理人




**Body-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
processDefinitionKey|string|流程图唯一标识|true|-
businessKey|string|业务关联键|true|-
variables|map|参数|true|-
└─any object|object|any object.|false|-
assgnee|string|代理人:[设置代理人接口必填]|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i /business/assgnee --data '{
  "processDefinitionKey": "4pix7w",
  "businessKey": "wmgra5",
  "variables": {
    "mapKey": {}
  },
  "assgnee": "fvhsxf"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
meta|object|No comments found.|-
└─success|boolean|No comments found.|-
└─message|string|No comments found.|-
└─code|int32|No comments found.|-
data|object|No comments found.|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 376
  },
  "data": true
}
```

## 查看评论列表
**URL:** `/business/comment`

**Type:** `GET`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 查看评论列表



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
businessKey|string|业务关联键|true|-


**Request-example:**
```
curl -X GET -i /business/comment?businessKey=itir9m
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
meta|object|No comments found.|-
└─success|boolean|No comments found.|-
└─message|string|No comments found.|-
└─code|int32|No comments found.|-
data|array|No comments found.|-
└─id|string|编号|-
└─userId|string|用户ID|-
└─time|string|评论时间|-
└─taskId|string|实例ID|-
└─processInstanceId|string|引用做出此评论的流程实例|-
└─type|string|对注释类型的引用|-
└─fullMessage|string|完整评论消息|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 212
  },
  "data": [
    {
      "id": "66",
      "userId": "66",
      "time": "2021-08-18 17:12:29",
      "taskId": "66",
      "processInstanceId": "66",
      "type": "lrqnue",
      "fullMessage": "success"
    }
  ]
}
```

## 提交任务
**URL:** `/business/completeByBusinessKey`

**Type:** `POST`

**Author:** mr.sanq

**Content-Type:** `application/json; charset=utf-8`

**Description:** 提交任务




**Body-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
processDefinitionKey|string|流程图唯一标识|true|-
businessKey|string|业务关联键|true|-
variables|map|参数|true|-
└─any object|object|any object.|false|-
assgnee|string|代理人:[设置代理人接口必填]|true|-

**Request-example:**
```
curl -X POST -H 'Content-Type: application/json; charset=utf-8' -i /business/completeByBusinessKey --data '{
  "processDefinitionKey": "dklyzk",
  "businessKey": "3io50m",
  "variables": {
    "mapKey": {}
  },
  "assgnee": "blgug7"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
meta|object|No comments found.|-
└─success|boolean|No comments found.|-
└─message|string|No comments found.|-
└─code|int32|No comments found.|-
data|object|No comments found.|-
└─taskId|string|实例ID|-
└─ok|boolean|是否提交成功|-
└─finished|boolean|流程是否结束|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 305
  },
  "data": {
    "taskId": "66",
    "ok": true,
    "finished": true
  }
}
```

## 待办任务
**URL:** `/business/getTaskListByAssignee`

**Type:** `GET`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 待办任务



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
businessKey|string|业务关联键|true|-
userId|string|     当前用户|true|-
isActive|boolean|   是否是活动状态|false|-


**Request-example:**
```
curl -X GET -i /business/getTaskListByAssignee?userId=66&businessKey=zrgzt1&isActive=true
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
meta|object|No comments found.|-
└─success|boolean|No comments found.|-
└─message|string|No comments found.|-
└─code|int32|No comments found.|-
data|array|No comments found.|-
└─assignee|string|拥有者|-
└─taskId|string|任务ID|-
└─taskName|string|任务名称|-
└─processDefinitionId|string|流程定义ID|-
└─processInstanceId|string|流程实例ID|-
└─executionId|string|执行对象ID|-
└─createTime|string|任务创办时间|-
└─processVariables|map|流程参数|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─any object|object|any object.|-
└─taskLocalVariables|map|当前任务参数|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─any object|object|any object.|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 30
  },
  "data": [
    {
      "assignee": "08s5ew",
      "taskId": "66",
      "taskName": "弘文.赵",
      "processDefinitionId": "66",
      "processInstanceId": "66",
      "executionId": "66",
      "createTime": "2021-08-18 17:12:29",
      "processVariables": {
        "mapKey": {}
      },
      "taskLocalVariables": {
        "mapKey": {}
      }
    }
  ]
}
```

## 撤销流程
**URL:** `/business/revokeFlow`

**Type:** `DELETE`

**Author:** mr.sanq

**Content-Type:** `application/json; charset=utf-8`

**Description:** 撤销流程




**Body-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
processDefinitionKey|string|流程图唯一标识|true|-
businessKey|string|业务关联键|true|-
userId|string|用户ID|true|-
reason|string|撤销原因|true|-

**Request-example:**
```
curl -X DELETE -H 'Content-Type: application/json; charset=utf-8' -i /business/revokeFlow --data '{
  "processDefinitionKey": "4ecavc",
  "businessKey": "wzv0bd",
  "userId": "66",
  "reason": "xjct9a"
}'
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
meta|object|No comments found.|-
└─success|boolean|No comments found.|-
└─message|string|No comments found.|-
└─code|int32|No comments found.|-
data|object|No comments found.|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 14
  },
  "data": true
}
```

## 历史流程
**URL:** `/business/getHistoryTaskList`

**Type:** `GET`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 历史流程



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
processDefinitionKey|string|流程唯一标识|false|-
businessKey|string|         业务关联键|false|-
userId|string|              当前用户|false|-
currentIndex|int32|        页码|false|-
pageSize|int32|            当前展示条数|false|-


**Request-example:**
```
curl -X GET -i /business/getHistoryTaskList?processDefinitionKey=k0igyg&businessKey=rbppxz&currentIndex=1&userId=66&pageSize=10
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
meta|object|No comments found.|-
└─success|boolean|No comments found.|-
└─message|string|No comments found.|-
└─code|int32|No comments found.|-
data|object|No comments found.|-
└─pagination|object|No comments found.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─pageSize|int32|No comments found.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─currentIndex|int32|No comments found.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─sorts|array|No comments found.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─field|string|No comments found.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─sorted|string|No comments found.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─totalCount|int64|No comments found.|-
└─datas|array|No comments found.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─assignee|string|拥有者|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─taskId|string|任务ID|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─taskName|string|任务名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─processDefinitionId|string|流程定义ID|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─processInstanceId|string|流程实例ID|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─executionId|string|执行对象ID|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─createTime|string|任务创办时间|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─processVariables|map|流程参数|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─any object|object|any object.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─taskLocalVariables|map|当前任务参数|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─any object|object|any object.|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─startTime|string|开始时间|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─endTime|string|结束时间|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─claimTime|string|领取任务的时间|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 182
  },
  "data": {
    "pagination": {
      "pageSize": 10,
      "currentIndex": 81,
      "sorts": [
        {
          "field": "fzspk3",
          "sorted": "43q6lq"
        }
      ],
      "totalCount": 137
    },
    "datas": [
      {
        "assignee": "3x1mu2",
        "taskId": "66",
        "taskName": "弘文.赵",
        "processDefinitionId": "66",
        "processInstanceId": "66",
        "executionId": "66",
        "createTime": "2021-08-18 17:12:29",
        "processVariables": {
          "mapKey": {}
        },
        "taskLocalVariables": {
          "mapKey": {}
        },
        "startTime": "2021-08-18 17:12:29",
        "endTime": "2021-08-18",
        "claimTime": "2021-08-18 17:12:29"
      }
    ]
  }
}
```

