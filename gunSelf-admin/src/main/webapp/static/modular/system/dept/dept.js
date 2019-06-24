/**
 * 部门管理初始化
 */
var Dept = {
    id: "DeptTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Dept.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', align: 'center', valign: 'middle',width:'50px'},
        {title: '部门简称', field: 'simplename', align: 'center', valign: 'middle', sortable: true},
        {title: '部门全称', field: 'fullname', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'num', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'tips', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 检查是否选中
 */
Dept.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Dept.seItem = selected[0];
        console.log(Dept.seItem)
        return true;
    }
};

/**
 * 点击添加部门
 */
Dept.openAddDept = function () {
    var index = layer.open({
        type: 2,
        title: '添加部门',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/dept/dept_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看部门详情
 */
Dept.openDeptDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '部门详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/dept/dept_update/' + Dept.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除部门
 */
Dept.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/dept/delete", function () {
                Feng.success("删除成功!");
                Dept.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deptId",Dept.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除"+Dept.seItem.simplename+"部门?", operation);
    }
};

/**
 * 撤销部门
 *
 */
Dept.cancel = function(){
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/dept/cancel", function (data) {
                console.log(data);
                if (data.code==500){
                    Feng.info(data.message);
                }else{
                    Feng.success(data.message);
                }
                Dept.table.refresh();
            }, function (data) {
                console.log(data);
                Feng.error("撤销失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deptId",Dept.seItem.id);
            ajax.start();
        };
        Feng.confirm("此操作会导致该部门下所有员工离职,确定要撤销吗?", operation);
    }
}

/**
 * 恢复部门
 *
 */
Dept.recovery = function(){
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/dept/recovery", function (data) {
                console.log(data);
                if (data.code==500){
                    Feng.info(data.message);
                }else{
                    Feng.success(data.message);
                }
                Dept.table.refresh();
            }, function (data) {
                console.log(data);
                Feng.error("恢复部门失败!" + data.responseJSON.message + "!");
            });
            console.log(Dept.seItem);
            ajax.set("deptId",Dept.seItem.id);
            ajax.start();
        };
        Feng.confirm("确定恢复部门吗?", operation);

    }
}

/**
 * 查询部门列表
 */
Dept.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    console.log($("#tips").val());
    queryData['tips'] = $("#tips").val();
    console.log(queryData);
    Dept.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Dept.initColumn();
    var table = new BSTreeTable(Dept.id, "/dept/list", defaultColunms);
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("pid");
    table.setExpandAll(true);
    table.init();
    Dept.table = table;
});
