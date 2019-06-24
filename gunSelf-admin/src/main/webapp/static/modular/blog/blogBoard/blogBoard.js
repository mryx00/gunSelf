/**
 * 博客留言管理初始化
 */
var BlogBoard = {
    id: "BlogBoardTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogBoard.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '留言内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '用户名', field: 'account', visible: true, align: 'center', valign: 'middle'},
            {title: '所属博客账户', field: 'blogAccount', visible: true, align: 'center', valign: 'middle'},
            {title: '发表时间', field: 'creatTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogBoard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogBoard.ids[i] = selected[i].id;
          }
        BlogBoard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加博客留言
 */
BlogBoard.openAddBlogBoard = function () {
    var index = layer.open({
        type: 2,
        title: '添加博客留言',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogBoard/blogBoard_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看博客留言详情
 */
BlogBoard.openBlogBoardDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '博客留言详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogBoard/blogBoard_update/' + BlogBoard.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除博客留言
 */
BlogBoard.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogBoard/delete", function (data) {
            Feng.success("删除成功!");
            BlogBoard.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询博客留言列表
 */
BlogBoard.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogBoard.table.refresh({query: queryData});
};

/**
 * 导出博客留言
 */
BlogBoard.export = function () {
    window.open("/blogBoard/export")
};

$(function () {
    var defaultColunms = BlogBoard.initColumn();
    var table = new BSTable(BlogBoard.id, "/blogBoard/list", defaultColunms);
    table.setPaginationType("server");
    BlogBoard.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogBoard/import/' //上传接口,
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
