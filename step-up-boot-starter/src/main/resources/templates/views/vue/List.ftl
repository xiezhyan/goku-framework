<template>
    <cab-body>
        <template slot="search">
            <a-form-model layout="inline" :model="search">
                <a-form-model-item>
                    <a-button type="primary" @click="get${table.javaName?cap_first}List()">搜索</a-button>
                </a-form-model-item>
                <a-form-model-item>
                    <a-button @click="resetSearch">重置</a-button>
                </a-form-model-item>
            </a-form-model>
        </template>

        <template slot="toolbar">
            <a-button type="primary" @click="visible = !visible" v-action:${table.javaName}:save>新增</a-button>
        </template>

        <template slot="body">
            <a-table
                :columns="columns"
                :dataSource="tableData"
                :rowKey="record => record.id"
                @change="handleLoadTable"
                :pagination="pagination"
            >
            <#list fields as field>
                <#if field.javaType == "Date">
                <span slot="slot_${field.columnName}" slot-scope="scope">{{scope|datetime}}</span>
                </#if>
            </#list>
                <span slot="slot_action" slot-scope="id, record">
                  <a-button
                      shape="circle"
                      icon="edit"
                      title="修改"
                      @click="updateById(record)"
                      v-action:${table.javaName}:update
                  ></a-button>
                  <span class="w-1"></span>
                  <a-button
                      type="danger"
                      shape="circle"
                      icon="delete"
                      title="刪除"
                      @click="deleteById(record)"
                      v-action:${table.javaName}:delete
                  ></a-button>
                </span>
            </a-table>

            <a-drawer
                :title="title"
                :width="720"
                :visible="visible"
                :bodyStyle="{paddingBottom: '80px'}"
                @close="onDrawerClose"
            >
                <a-form-model :model="form" :rules="rules" ref="${table.javaName}Form">
                    <#list fields as field>
                    <#if !field.priKey && field.javaType != "Date">
                    <a-form-model-item label="${field.columnComment}" prop="${field.javaColumnName}">
                        <a-input v-model="form.${field.javaColumnName}" allowClear />
                    </a-form-model-item>
                    </#if>
                    </#list>
                    <div class="drawer-footer">
                        <a-button @click="onDrawerClose">取消</a-button>
                        <a-button @click="onSubmit" type="primary">提交</a-button>
                    </div>
                </a-form-model>
            </a-drawer>
        </template>
    </cab-body>
</template>

<script>
    import {
        get${table.javaName?cap_first}List,
        save${table.javaName?cap_first},
        delete${table.javaName?cap_first},
        update${table.javaName?cap_first}ById
    } from "api/${table.javaName}/${table.javaName?cap_first}Api";

    import {
        Table,
        Button,
        FormModel,
        Input,
        Select,
        Drawer,
        Radio,
        Tag
    } from "ant-design-vue";

    const columns = [
        <#list fields as field>
        {
            title: "${field.columnComment}",
            dataIndex: "${field.javaColumnName}",
            key: "${field.javaColumnName}",
            slots: { title: "column_${field.columnName}" }, // 设置表头
            scopedSlots: { customRender: "slot_${field.columnName}" } // 设置内容
        },
        </#list>
        {
            title: "操作",
            dataIndex: "action",
            key: "action",
            scopedSlots: { customRender: "slot_action" }
        }
    ];

    import { Status } from "assets/js/constrant";
    import { confirm, show, set2Form, clearForm } from "utils/Base";

    export default {
        name: "DeptList",
        components: {
            AButton: Button,
            ATable: Table,
            AFormModel: FormModel,
            AFormModelItem: FormModel.Item,
            AInput: Input,
            ASelect: Select,
            ASelectOption: Select.Option,
            ADrawer: Drawer,
            ARadio: Radio,
            ARadioGroup: Radio.Group,
            ATag: Tag
        },
        data() {
            return {
                columns,
                tableData: [],
                search: {
                    currentIndex: 1
                },
                pagination: {
                    pageSize: 20
                },
                title: "添加${table.tableComment}",
                visible: false,
                form: {
                <#list fields as field>
                <#if field.javaType != "Date">
                    ${field.javaColumnName}: '' <#if field_index + 1 != fields?size>,</#if>
                </#if>
                </#list>
                },
                common_status: Status,
                rules: {
                <#list fields as field>
                <#if field.javaType != "Date">
                    ${field.javaColumnName}: [{ required: true, message: "请输入${field.columnComment}"}] <#if field_index + 1 != fields?size>,</#if>
                </#if>
                </#list>
                }
            };
        },
        methods: {
            resetSearch () {
                clearForm(this.search);
                this.search.currentIndex = 1;

                this.get${table.javaName?cap_first}List();
            },
            get${table.javaName?cap_first}List() {
                get${table.javaName?cap_first}List(this.search).then(res => {
                    if (res) {
                        this.tableData = res.datas;
                        const pagination = { ...this.pagination };
                        pagination.total = res.pagination.totalCount;

                        this.pagination = pagination;
                    }
                });
            },
            handleLoadTable(pagination, filters, sorter) {
                this.search.currentIndex = pagination.current;

                this.get${table.javaName?cap_first}List();
            },
            onSubmit() {
                this.$refs.${table.javaName}Form.validate(valid => {
                    if (valid) {
                        if (this.form.id) {
                            update${table.javaName?cap_first}ById(this.form).then(res => {
                                if (res) {
                                    show("修改成功");
                                    this.visible = false;
                                    this.get${table.javaName?cap_first}List();
                                }
                            });
                        } else {
                            save${table.javaName?cap_first}(this.form).then(res => {
                                if (res) {
                                    show("添加成功");
                                    this.visible = false;
                                    this.get${table.javaName?cap_first}List();
                                }
                            });
                        }
                    }
                });
            },
            deleteById(record) {
                confirm(() => {
                    delete${table.javaName?cap_first}({
                        id: record.id
                    }).then(res => {
                        if (res) {
                            show("删除成功");
                            this.get${table.javaName?cap_first}List();
                        }
                    });
                }, "确定删除编号:" + record.id + " 的数据么？");
            },
            updateById(record) {
                set2Form(this.form, record);
                this.visible = true;
                this.title = "修改${table.tableComment}";
            },
            onDrawerClose() {
                this.visible = !this.visible;
                clearForm(this.form);
            }
        },
        created() {
            this.get${table.javaName?cap_first}List();
        },
        watch: {
            visible(newVisiable) {
                if (newVisiable) {
                    //
                }
            }
        }
    };
</script>

<style lang="less" scoped>
</style>