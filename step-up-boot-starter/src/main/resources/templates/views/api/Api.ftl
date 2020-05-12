import $axios from 'utils/Request.js'

//数据列表
export const get${table.javaName?cap_first}List = data => {
    return $axios({
        url: '/v1/api/${table.javaName}/',
        method: 'get',
        data
    });
}

//获取详情
export const get${table.javaName?cap_first} = data => {
    return $axios({
        url: '/v1/api/${table.javaName}/' + data.id,
        method: 'get',
        data
    });
}

//保存
export const save${table.javaName?cap_first} = data => {
    return $axios({
        url: '/v1/api/${table.javaName}/',
        method: 'post',
        data
    });
}

//删除数据
export const delete${table.javaName?cap_first} = data => {
    return $axios({
        url: '/v1/api/${table.javaName}/' + data.id,
        method: 'delete',
        data
    });
}

//修改数据
export const update${table.javaName?cap_first}ById = data => {
    return $axios({
        url: '/v1/api/${table.javaName}/' + data.id,
        method: 'put',
        data
    });
}