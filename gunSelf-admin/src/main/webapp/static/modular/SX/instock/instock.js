/**
 * 入库管理初始化
 */
var Instock = {
    id: "InstockTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Instock.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '编号', field: 'instockId', visible: true, align: 'center', valign: 'middle'},
            {title: '产品名称', field: 'productId', visible: true, align: 'center', valign: 'middle'},
            {title: '入库申请人', field: 'userId', visible: true, align: 'center', valign: 'middle'},
            {title: '入库备注', field: 'description', visible: true, align: 'center', valign: 'middle'},
            {title: '入库数量', field: 'instockNum', visible: true, align: 'center', valign: 'middle'},
            {title: '入库时间', field: 'instockTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Instock.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Instock.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加入库
 */
Instock.openAddInstock = function () {
    var index = layer.open({
        type: 2,
        title: '添加入库',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/instock/instock_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看入库详情
 */
Instock.openInstockDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '入库详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/instock/instock_update/' + Instock.seItem.instockId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除入库
 */
Instock.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/instock/delete", function (data) {
            Feng.success("删除成功!");
            Instock.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("instockId",this.seItem.instockId );
        ajax.start();
    }
};

/**
 * 查询入库列表
 */
Instock.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Instock.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Instock.initColumn();
    var table = new BSTable(Instock.id, "/instock/list", defaultColunms);
    table.setPaginationType("server");
    Instock.table = table.init();
});
