package com.stylefeng.gunSelf.generator.action.model;

import java.io.Serializable;
import java.util.List;

public class TableFieldsVo implements Serializable {
    private List<MyTableField> tableFields;

    private  String tableName;

    public TableFieldsVo(List<MyTableField> tableFields) {
        this.tableFields = tableFields;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public TableFieldsVo() {

    }

    public List<MyTableField> getTableFields() {
        return tableFields;
    }

    public void setTableFields(List<MyTableField> tableFields) {
        this.tableFields = tableFields;
    }
}
