/**
 * 订单管理初始化
 */
var Orderitem = {
    id: "OrderitemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Orderitem.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '订单编号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
            {title: '客户姓名', field: 'customerNo', visible: true, align: 'center', valign: 'middle'},
            {title: '下单员工', field: 'employeeNo', visible: true, align: 'center', valign: 'middle'},
            {title: '下单时间', field: 'orderTime', visible: true, align: 'center', valign: 'middle'},
            {title: '订单状态', field: 'orderState', visible: true, align: 'center', valign: 'middle'},
            {title: '产品数量', field: 'protectNumber', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Orderitem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Orderitem.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加订单
 */
Orderitem.openAddOrderitem = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/orderitem/orderitem_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看订单详情
 */
Orderitem.openOrderitemDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '订单详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/orderitem/orderitem_update/' + Orderitem.seItem.orderNo
        });
        this.layerIndex = index;
    }
};

/**
 * 删除订单
 */
Orderitem.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/orderitem/delete", function (data) {
            Feng.success("删除成功!");
            Orderitem.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("orderitemId",this.seItem.orderNo );
        ajax.start();
    }
};

/**
 * 查询订单列表
 */
Orderitem.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Orderitem.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Orderitem.initColumn();
    var table = new BSTable(Orderitem.id, "/orderitem/list", defaultColunms);
    table.setPaginationType("server");
    Orderitem.table = table.init();
});
