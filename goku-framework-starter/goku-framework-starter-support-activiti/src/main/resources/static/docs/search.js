let api = [];
api.push({
    alias: 'api',
    order: '1',
    desc: '实例操作',
    link: '实例操作',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '启动流程',
});
api[0].list.push({
    order: '2',
    desc: '设置代理人',
});
api[0].list.push({
    order: '3',
    desc: '查看评论列表',
});
api[0].list.push({
    order: '4',
    desc: '提交任务',
});
api[0].list.push({
    order: '5',
    desc: '待办任务',
});
api[0].list.push({
    order: '6',
    desc: '撤销流程',
});
api[0].list.push({
    order: '7',
    desc: '历史流程',
});
api.push({
    alias: 'StencilsetRestResource',
    order: '2',
    desc: '系统自带',
    link: '系统自带',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '',
});
api.push({
    alias: 'ModelRestController',
    order: '3',
    desc: '流程操作',
    link: '流程操作',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '跳转到可视化界面',
});
api[2].list.push({
    order: '2',
    desc: '分页获取流程数据',
});
api[2].list.push({
    order: '3',
    desc: '发布流程',
});
api[2].list.push({
    order: '4',
    desc: '删除流程',
});
api[2].list.push({
    order: '5',
    desc: '通过processDefinitionId获取当前Model',
});
api[2].list.push({
    order: '6',
    desc: '查看当前流程图',
});
api.push({
    alias: 'ModelSaveRestResource',
    order: '4',
    desc: '系统自带',
    link: '系统自带',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '保存流程',
});
api[3].list.push({
    order: '2',
    desc: '',
});
api.push({
    alias: 'dict',
    order: '5',
    desc: '数据字典',
    link: 'dict_list',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    alias: apiData.alias,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        alias: apiData.alias,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
         for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="' + apiData[j].alias + '.html#header">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="' + apiData[j].alias + '.html#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}