/**
 * 模型管理初始化
 */
var Activitimodel = {
    id: "ActivitimodelTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Activitimodel.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '模型编号', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '模型名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '版本', field: 'version', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '最新更新时间', field: 'lastUpdateTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Activitimodel.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Activitimodel.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加模型
 */
Activitimodel.openAddActivitimodel = function () {
    // var index = layer.open({
    //     type: 2,
    //     title: '添加模型',
    //     area: ['800px', '420px'], //宽高
    //     fix: false, //不固定
    //     maxmin: true,
    //     content: Feng.ctxPath + '/activitimodel/activitimodel_add'
    // });
    // this.layerIndex = index;
    window.open(Feng.ctxPath +" /activitimodel/activitimodel_add");
};

/**
 * 打开查看模型详情
 */
Activitimodel.openActivitimodelDetail = function () {
    if (this.check()) {
        // var index = layer.open({
        //     type: 2,
        //     title: '模型详情',
        //     area: ['800px', '420px'], //宽高
        //     fix: false, //不固定
        //     maxmin: true,
        //     content: Feng.ctxPath + '/activitimodel/activitimodel_update/' + Activitimodel.seItem.modelId
        // });
        // this.layerIndex = index;
        window.open(Feng.ctxPath +" /activitimodel/activitimodel_update/"+Activitimodel.seItem.id)
    }
};

/**
 * 删除模型
 */
Activitimodel.delete = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/activitimodel/delete", function (data) {
                Feng.success("删除成功!");
                Activitimodel.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("activitimodelId",Activitimodel.seItem.id );
            ajax.start();
        }

    }
    Feng.confirm("是否删除模型" + Activitimodel.seItem.name + "?",operation);
};


/**
 * 部署模型
 */
Activitimodel.deploy = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/activitimodel/deploy", function (data) {
                if(data.code == 500){
                    Feng.error(data.message);
                }
                Feng.success("部署成功!");
                Activitimodel.table.refresh();
            }, function (data) {
                Feng.error("部署失败!" + data.responseJSON.message + "!");
            });
            ajax.set("activitimodelId",Activitimodel.seItem.id );
            ajax.start();
        }

    }
    Feng.confirm("是否部署模型" + Activitimodel.seItem.name + "?",operation);
};

/**
 * 查询模型列表
 */
Activitimodel.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Activitimodel.table.refresh({query: queryData});
};
/**
 * 格式化日期
 */
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
    var defaultColunms = Activitimodel.initColumn();
    var table = new BSTable(Activitimodel.id, "/activitimodel/list", defaultColunms);
    table.setPaginationType("server");
    Activitimodel.table = table.init();
});
