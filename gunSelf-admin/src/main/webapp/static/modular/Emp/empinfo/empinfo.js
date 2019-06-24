/**
 * 管理初始化
 */
var Empinfo = {
    id: "EmpinfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Empinfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '员工编号', field: 'empNo', visible: true, align: 'center', valign: 'middle'},
            {title: '员工姓名', field: 'empName', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'empSex', visible: true, align: 'center', valign: 'middle'},
            {title: '年龄', field: 'empAge', visible: true, align: 'center', valign: 'middle'},
            {title: '入职日期', field: 'empDate', visible: true, align: 'center', valign: 'middle'},
            {title: '部门', field: 'deptId', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Empinfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Empinfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
Empinfo.openAddEmpinfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/empinfo/empinfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
Empinfo.openEmpinfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/empinfo/empinfo_update/' + Empinfo.seItem.empNo
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
Empinfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/empinfo/delete", function (data) {
            Feng.success("删除成功!");
            Empinfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("empinfoId",this.seItem.empNo );
        ajax.start();
    }
};

/**
 * 查询列表
 */
Empinfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Empinfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Empinfo.initColumn();
    var table = new BSTable(Empinfo.id, "/empinfo/list", defaultColunms);
    table.setPaginationType("server");
    Empinfo.table = table.init();
});
