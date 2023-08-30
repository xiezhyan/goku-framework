# BusinessRestActivitiController

BusinessRestActivitiController


---
## ��ת�����ӻ�����

> BASIC

**Path:** /rest/to

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| modelId |  | NO | ���̱�� |
| tenantId |  | NO | �⻧ |
| category |  | NO | ���� |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  |
| &ensp;&ensp;&#124;��success | boolean | �����Ƿ�ɹ� |
| &ensp;&ensp;&#124;��message | string | ������Ϣ |
| &ensp;&ensp;&#124;��code | integer | ���ر��� |
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
## ��������

> BASIC

**Path:** /rest/deploy/{modelId}

**Method:** POST

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| modelId |  | ����ID |

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
| &ensp;&ensp;&#124;��success | boolean | �����Ƿ�ɹ� |
| &ensp;&ensp;&#124;��message | string | ������Ϣ |
| &ensp;&ensp;&#124;��code | integer | ���ر��� |
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
## ɾ������

> BASIC

**Path:** /rest/{modelId}

**Method:** DELETE

> REQUEST

**Path Params:**

| name | value | desc |
| ------------ | ------------ | ------------ |
| modelId |  | ����ID |

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
| &ensp;&ensp;&#124;��success | boolean | �����Ƿ�ɹ� |
| &ensp;&ensp;&#124;��message | string | ������Ϣ |
| &ensp;&ensp;&#124;��code | integer | ���ر��� |
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
## ��ҳ��ȡ��������

> BASIC

**Path:** /rest/page

**Method:** GET

> REQUEST

**Query:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| pageSize |  | NO | ��ǰ��ʾ������,Ĭ����ʾ100�� |
| currentIndex |  | NO | ��ǰ��ʾ��ҳ�� |
| totalCount |  | NO | ��ѯ���� |
| sorteds[0].field |  | NO | �ֶ� |
| sorteds[0].orderBy |  | NO | ����ʽ |
| sorteds[0].lineField |  | NO | ת��֮���ֶ� |
| name |  | NO | �������� |
| key |  | NO | Ψһ��ʶ |
| category |  | NO | ���� |
| tenantId |  | NO | �⻧ |



> RESPONSE

**Headers:**

| name | value | required | desc |
| ------------ | ------------ | ------------ | ------------ |
| content-type | application/json;charset=UTF-8 | NO |  |

**Body:**

| name | type | desc |
| ------------ | ------------ | ------------ |
| meta | object |  |
| &ensp;&ensp;&#124;��success | boolean | �����Ƿ�ɹ� |
| &ensp;&ensp;&#124;��message | string | ������Ϣ |
| &ensp;&ensp;&#124;��code | integer | ���ر��� |
| data | object |  |
| &ensp;&ensp;&#124;��pagination | object | ��ҳ���� |
| &ensp;&ensp;&ensp;&ensp;&#124;��pageSize | integer | ��ǰ��ʾ������,Ĭ����ʾ100�� |
| &ensp;&ensp;&ensp;&ensp;&#124;��currentIndex | integer | ��ǰ��ʾ��ҳ�� |
| &ensp;&ensp;&ensp;&ensp;&#124;��totalCount | integer | ��ѯ���� |
| &ensp;&ensp;&ensp;&ensp;&#124;��sorteds | array | ����ʽ |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;�� | object |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��field | string | �ֶ� |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��orderBy | string | ����ʽ |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��lineField | string | ת��֮���ֶ� |
| &ensp;&ensp;&#124;��datas | array | ����� |
| &ensp;&ensp;&ensp;&ensp;&#124;�� | object |  |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��id | string | ��� |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��name | string | ���� |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��key | string | Ψһ��ʶ |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��category | string | ���� |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��createTime | string | ����ʱ�� |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��lastUpdateTime | string | ���һ���޸�ʱ�� |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��metaInfo | string | Ԫ������Ϣ |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��deploymentId | string | ����ID |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��version | integer | �汾�� |
| &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&#124;��tenantId | string | �⻧ |

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



