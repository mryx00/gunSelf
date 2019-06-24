/**
 * 管理初始化
 */
var People = {
    id: "PeopleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
People.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '名字', field: 'name', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
People.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        People.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
People.openAddPeople = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/people/people_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
People.openPeopleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/people/people_update/' + People.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
People.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/people/delete", function (data) {
            Feng.success("删除成功!");
            People.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("peopleId",this.seItem.id );
        ajax.start();
    }
};

/**
 * 查询列表
 */
People.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    People.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = People.initColumn();
    var table = new BSTable(People.id, "/people/list", defaultColunms);
    table.setPaginationType("server");
    People.table = table.init();
});
