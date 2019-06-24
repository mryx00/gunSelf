/**
 * 客户管理初始化
 */
var Customer = {
    id: "CustomerTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Customer.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '编号', field: 'customerNo', visible: true, align: 'center', valign: 'middle'},
            {title: '客户姓名', field: 'customerName', visible: true, align: 'center', valign: 'middle'},
            {title: '联系方式', field: 'contactInformation', visible: true, align: 'center', valign: 'middle'},
            {title: '客户地址', field: 'address', visible: true, align: 'center', valign: 'middle'},
            {title: '所属员工', field: 'belongUser', visible: true, align: 'center', valign: 'middle'},
            {title: '是否删除', field: 'isDelete', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Customer.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Customer.seItem = selected[0];
        console.log(Customer.seItem);
        return true;
    }
};

/**
 * 点击添加客户
 */
Customer.openAddCustomer = function () {
    var index = layer.open({
        type: 2,
        title: '添加客户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/customer/customer_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看客户详情
 */
Customer.openCustomerDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '客户详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/customer/customer_update/' + Customer.seItem.customerNo
        });
        this.layerIndex = index;
    }
};

/**
 * 删除客户
 */
Customer.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/customer/delete", function (data) {
                Feng.success("删除成功!");
                Customer.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            console.log(Customer.seItem);
            ajax.set("customerId",Customer.seItem.customerNo );
            ajax.start();
        };
    }
    Feng.confirm("是否删除客户" + Customer.seItem.customerName + "?",operation);
};

/**
 * 恢复客户
 */
Customer.recovery = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/customer/recovery", function (data) {
                if(data.code==500){
                    Feng.info(data.message);
                }else{
                    Feng.success(data.message);
                }
                Customer.table.refresh();
            }, function (data) {
                Feng.error("恢复失败!" + data.responseJSON.message + "!");
            });
            ajax.set("customerId",Customer.seItem.customerNo );
            ajax.start();
        };
    }
    Feng.confirm("是否恢复客户" + Customer.seItem.customerName + "?",operation);
};

/**
 * 查询客户列表
 */
Customer.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['isDelete'] = $("#isDelete").val();
    Customer.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Customer.initColumn();
    var table = new BSTable(Customer.id, "/customer/list", defaultColunms);
    table.setPaginationType("server");
    Customer.table = table.init();
});
