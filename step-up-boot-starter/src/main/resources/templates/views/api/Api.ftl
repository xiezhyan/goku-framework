import $axios from 'utils/Request.js'

//数据列表
export const get${table.javaName?cap_first}List = data => {
    return $axios({
        url: '/api/${apiName}/',
        method: 'get',
        data
    });
}

//获取详情
export const get${table.javaName?cap_first} = data => {

    return $axios({
        url: '/api/${apiName}/' + data.id,
        method: 'get',
        data
    });
}

//保存
export const save${table.javaName?cap_first} = data => {
    return $axios({
        url: '/api/${apiName}/',
        method: 'post',
        data
    });
}

//删除数据
export const delete${table.javaName?cap_first} = data => {
    return $axios({
        url: '/api/${apiName}/' + data.id,
        method: 'delete',
        data
    });
}

//修改数据
export const update${table.javaName?cap_first}ById = data => {
    return $axios({
        url: '/api/${apiName}/' + data.id,
        method: 'put',
        data
    });
}