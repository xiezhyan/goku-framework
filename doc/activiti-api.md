# BusinessRestActivitiController

BusinessRestActivitiController


---
## 跳转到可视化界面

> BASIC

**Path:** /rest/to

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| modelId |  | NO | 流程编号 |
| tenantId |  | NO | 租户 |
| category |  | NO | 分类 |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  |
| &ensp;&ensp;&#124;─success | boolean | 操作是否成功 |
| &ensp;&ensp;&#124;─message | string | 返回消息 |
| &ensp;&ensp;&#124;─code | integer | 返回编码 |
| data | string |  |

**Response Demo:**

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




---
## 发布流程

> BASIC

**Path:** /rest/deploy/{modelId}

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| modelId |  | 流程ID |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  |
| &ensp;&ensp;&#124;─success | boolean | 操作是否成功 |
| &ensp;&ensp;&#124;─message | string | 返回消息 |
| &ensp;&ensp;&#124;─code | integer | 返回编码 |
| data | boolean |  |

**Response Demo:**

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
## 删除流程

> BASIC

**Path:** /rest/{modelId}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| modelId |  | 流程ID |

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| Content-Type | application/x-www-form-urlencoded | YES |  |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  |
| &ensp;&ensp;&#124;─success | boolean | 操作是否成功 |
| &ensp;&ensp;&#124;─message | string | 返回消息 |
| &ensp;&ensp;&#124;─code | integer | 返回编码 |
| data | boolean |  |

**Response Demo:**

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
## 分页获取流程数据

> BASIC

**Path:** /rest/page

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| pageSize |  | NO | 当前显示的条数,默认显示100条 |
| currentIndex |  | NO | 当前显示的页数 |
| totalCount |  | NO | 查询总数 |
| sorteds[0].field |  | NO | 字段 |
| sorteds[0].orderBy |  | NO | 排序方式 |
| sorteds[0].lineField |  | NO | 转换之后字段 |
| name |  | NO | 流程名称 |
| key |  | NO | 唯一标识 |
| category |  | NO | 分类 |
| tenantId |  | NO | 租户 |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  |
| &ensp;&ensp;&#124;─success | boolean | 操作是否成功 |
| &ensp;&ensp;&#124;─message | string | 返回消息 |
| &ensp;&ensp;&#124;─code | integer | 返回编码 |
| data | object |  |
| &ensp;&ensp;&#124;─pagination | object | 分页参数 |
| &ensp;&ensp;&ensp;&ensp;&#124;─pageSize | integer | 当前显示的条数,默认显示100条 |
| &ensp;&ensp;&ensp;&ensp;&#124;─currentIndex | integer | 当前显示的页数 |
| &ensp;&ensp;&ensp;&ensp;&#124;─totalCount | integer | 查询总数 |
| &ensp;&ensp;&ensp;&ensp;&#124;─sorteds | array | 排序方式 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─ | object |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─field | string | 字段 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─orderBy | string | 排序方式 |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─lineField | string | 转换之后字段 |
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
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;─tenantId | string | 租户 |

**Response Demo:**

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
      "totalCount": 0,
      "sorteds": [
        {
          "field": "",
          "orderBy": "",
          "lineField": ""
        }
      ]
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
        "version": 0,
        "tenantId": ""
      }
    ]
  }
}
```



