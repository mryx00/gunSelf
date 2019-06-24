/**
 * 管理初始化
 */
var BlogView = {
    id: "BlogViewTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogView.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '浏览人id', field: 'viewPersonId', visible: true, align: 'center', valign: 'middle'},
            {title: '文章Id', field: 'articleId', visible: true, align: 'center', valign: 'middle'},
            {title: '查看时间', field: 'viewDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogView.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogView.ids[i] = selected[i].id;
          }
        BlogView.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
BlogView.openAddBlogView = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogView/blogView_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
BlogView.openBlogViewDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogView/blogView_update/' + BlogView.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
BlogView.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogView/delete", function (data) {
            Feng.success("删除成功!");
            BlogView.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询列表
 */
BlogView.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogView.table.refresh({query: queryData});
};

/**
 * 导出
 */
BlogView.export = function () {
    window.open("/blogView/export")
};

$(function () {
    var defaultColunms = BlogView.initColumn();
    var table = new BSTable(BlogView.id, "/blogView/list", defaultColunms);
    table.setPaginationType("server");
    BlogView.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogView/import/' //上传接口,
                ,accept: 'file'
                ,done: function(res){
                    //上传完毕回调
                    Feng.success("导入成功");
                    table.refresh();
                }
                ,error: function(){
                    //请求异常回调

                }
            });
        });
});
