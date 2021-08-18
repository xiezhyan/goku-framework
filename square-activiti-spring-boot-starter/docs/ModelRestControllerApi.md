
# 流程操作
## 跳转到可视化界面
**URL:** `/model/rest/creator`

**Type:** `GET`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 跳转到可视化界面



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
modelId|string| 流程编号|false|-
tenantId|string|租户|false|-
category|string|分类|false|-


**Request-example:**
```
curl -X GET -i /model/rest/creator?modelId=66&category=9v078z&tenantId=66
```

**Response-example:**
```
Doesn't return a value.
```

## 分页获取流程数据
**URL:** `/model/rest/list`

**Type:** `GET`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 分页获取流程数据



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
name|string|        流程名称|false|-
key|string|         唯一标识|false|-
category|string|    分类|false|-
tenantId|string|    租户|false|-
currentIndex|int32|页码|false|-
pageSize|int32|    显示条数|false|-


**Request-example:**
```
curl -X GET -i /model/rest/list?category=gi3qei&tenantId=66&currentIndex=1&name=弘文.赵&key=m6qfte&pageSize=10
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
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─id|string|编号|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─name|string|名称|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─key|string|唯一标识|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─category|string|分类|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─createTime|string|创建时间|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─lastUpdateTime|string|最后一次修改时间|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─metaInfo|string|元数据信息|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─deploymentId|string|部署ID|-
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└─version|int32|版本号|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 315
  },
  "data": {
    "pagination": {
      "pageSize": 10,
      "currentIndex": 397,
      "sorts": [
        {
          "field": "3ks821",
          "sorted": "v85662"
        }
      ],
      "totalCount": 851
    },
    "datas": [
      {
        "id": "66",
        "name": "弘文.赵",
        "key": "d1ejws",
        "category": "wrwxxp",
        "createTime": "2021-08-18 17:12:29",
        "lastUpdateTime": "2021-08-18 17:12:29",
        "metaInfo": "b621hi",
        "deploymentId": "66",
        "version": 187
      }
    ]
  }
}
```

## 发布流程
**URL:** `/model/rest/deploy/{modelId}`

**Type:** `POST`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 发布流程


**Path-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
modelId|string|流程ID|true|-



**Request-example:**
```
curl -X POST -i /model/rest/deploy/66
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
    "code": 287
  },
  "data": true
}
```

## 删除流程
**URL:** `/model/rest/{modelId}`

**Type:** `DELETE`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 删除流程


**Path-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
modelId|string|流程ID|true|-



**Request-example:**
```
curl -X DELETE -i /model/rest/66
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
    "code": 310
  },
  "data": true
}
```

## 通过 processDefinitionId 获取当前Model
**URL:** `/model/rest/{processDefinitionId}`

**Type:** `GET`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 通过 processDefinitionId 获取当前Model


**Path-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
processDefinitionId|string|processDefinitionId|true|-



**Request-example:**
```
curl -X GET -i /model/rest/66
```
**Response-fields:**

Field | Type|Description|Since
---|---|---|---
meta|object|No comments found.|-
└─success|boolean|No comments found.|-
└─message|string|No comments found.|-
└─code|int32|No comments found.|-
data|object|No comments found.|-
└─id|string|编号|-
└─name|string|名称|-
└─key|string|唯一标识|-
└─category|string|分类|-
└─createTime|string|创建时间|-
└─lastUpdateTime|string|最后一次修改时间|-
└─metaInfo|string|元数据信息|-
└─deploymentId|string|部署ID|-
└─version|int32|版本号|-

**Response-example:**
```
{
  "meta": {
    "success": true,
    "message": "success",
    "code": 751
  },
  "data": {
    "id": "66",
    "name": "弘文.赵",
    "key": "tcov84",
    "category": "nmcchj",
    "createTime": "2021-08-18 17:12:29",
    "lastUpdateTime": "2021-08-18 17:12:29",
    "metaInfo": "l9efhi",
    "deploymentId": "66",
    "version": 629
  }
}
```

## 查看当前流程图
**URL:** `/model/rest/viewPic`

**Type:** `GET`

**Author:** mr.sanq

**Content-Type:** `application/x-www-form-urlencoded;charset=utf-8`

**Description:** 查看当前流程图



**Query-parameters:**

Parameter|Type|Description|Required|Since
---|---|---|---|---
processDefinitionKey|string|流程定义key|true|-


**Request-example:**
```
curl -X GET -i /model/rest/viewPic?processDefinitionKey=8839ly
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
    "code": 176
  },
  "data": "eyb5as"
}
```

