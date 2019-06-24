/**
 * 模型管理初始化
 */
var Deploymodel = {
    id: "DeploymodelTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Deploymodel.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '部署Id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '部署名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '部署时间', field: 'deploymentTime', visible: true, align: 'center', valign: 'middle',formatter:function(value,row,index){
                return formatterDate(value);
            }},
            {
                title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (value, row, index) {
                        return '<button type="button" class="layui-btn layui-btn-sm" onclick="deploymodel.detailInfo(' + row.id + ')" id=""><i class="fa fa-info">查看详情</i>&nbsp;</button>'
                }
            }
    ];
};

/**
 * 检查是否选中
 */
Deploymodel.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Deploymodel.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加模型
 */
Deploymodel.openAddDeploymodel = function () {
    var index = layer.open({
        type: 2,
        title: '添加模型',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deploymodel/deploymodel_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看模型详情
 */
Deploymodel.openDeploymodelDetail = function () {
    console.log("aaaa1123");
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + '/deploymodel/start/' + Deploymodel.seItem.id,function () {
            Feng.success("启动成功!");
        },function (data) {
            Feng.error("启动失败!" + data.responseJSON.message + "!");
        });
        ajax.start();
    }
};

/**
 * 删除模型
 */
Deploymodel.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/deploymodel/delete", function (data) {
            Feng.success("删除成功!");
            Deploymodel.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("deploymodelId",this.seItem.id );
        ajax.start();
    }
};

/**
 * 查询模型列表
 */
Deploymodel.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Deploymodel.table.refresh({query: queryData});
};
function formatterDate(value){
    var date = new Date(value);
    var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
    var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    var milliseconds = date.getMilliseconds();
    return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
}

$(function () {
    var defaultColunms = Deploymodel.initColumn();
    var table = new BSTable(Deploymodel.id, "/deploymodel/list", defaultColunms);
    table.setPaginationType("server");
    Deploymodel.table = table.init();
});
