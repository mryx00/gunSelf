/**
 * 博客外链接管理初始化
 */
var BlogLink = {
    id: "BlogLinkTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogLink.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '链接名', field: 'linkName', visible: true, align: 'center', valign: 'middle'},
            {title: '外链接地址', field: 'linkAddr', visible: true, align: 'center', valign: 'middle'},
            {title: '账户名', field: 'account', visible: true, align: 'center', valign: 'middle'},
            {title: '点击次数', field: 'count', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'order', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogLink.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogLink.ids[i] = selected[i].id;
          }
        BlogLink.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加博客外链接
 */
BlogLink.openAddBlogLink = function () {
    var index = layer.open({
        type: 2,
        title: '添加博客外链接',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogLink/blogLink_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看博客外链接详情
 */
BlogLink.openBlogLinkDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '博客外链接详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogLink/blogLink_update/' + BlogLink.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除博客外链接
 */
BlogLink.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogLink/delete", function (data) {
            Feng.success("删除成功!");
            BlogLink.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询博客外链接列表
 */
BlogLink.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogLink.table.refresh({query: queryData});
};

/**
 * 导出博客外链接
 */
BlogLink.export = function () {
    window.open("/blogLink/export")
};

$(function () {
    var defaultColunms = BlogLink.initColumn();
    var table = new BSTable(BlogLink.id, "/blogLink/list", defaultColunms);
    table.setPaginationType("server");
    BlogLink.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogLink/import/' //上传接口,
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
