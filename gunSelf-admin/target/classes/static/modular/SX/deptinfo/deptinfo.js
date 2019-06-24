/**
 * 部门管理初始化
 */
var Deptinfo = {
    id: "DeptinfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Deptinfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '编号', field: 'deptId', visible: true, align: 'center', valign: 'middle'},
            {title: '部门名称', field: 'deptName', visible: true, align: 'center', valign: 'middle'},
            {title: '部门备注', field: 'deptDesp', visible: true, align: 'center', valign: 'middle'},
            {title: '是否删除', field: 'isDelete', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Deptinfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Deptinfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加部门
 */
Deptinfo.openAddDeptinfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加部门',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deptinfo/deptinfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看部门详情
 */
Deptinfo.openDeptinfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '部门详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deptinfo/deptinfo_update/' + Deptinfo.seItem.deptId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除部门
 */
Deptinfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/deptinfo/delete", function (data) {
            Feng.success("删除成功!");
            Deptinfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("deptinfoId",this.seItem.deptId );
        ajax.start();
    }
};

/**
 * 查询部门列表
 */
Deptinfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Deptinfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Deptinfo.initColumn();
    var table = new BSTable(Deptinfo.id, "/deptinfo/list", defaultColunms);
    table.setPaginationType("server");
    Deptinfo.table = table.init();
});
