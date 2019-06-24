/**
 * 博客标签管理初始化
 */
var BlogTag = {
    id: "BlogTagTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogTag.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '标签名', field: 'tagName', visible: true, align: 'center', valign: 'middle'},
            {title: '计数', field: 'count', visible: false, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'creatTime', visible: true, align: 'center', valign: 'middle'},
            {title: '用户', field: 'account', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogTag.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogTag.ids[i] = selected[i].id;
          }
        BlogTag.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加博客标签
 */
BlogTag.openAddBlogTag = function () {
    var index = layer.open({
        type: 2,
        title: '添加博客标签',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogTag/blogTag_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看博客标签详情
 */
BlogTag.openBlogTagDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '博客标签详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogTag/blogTag_update/' + BlogTag.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除博客标签
 */
BlogTag.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogTag/delete", function (data) {
            Feng.success("删除成功!");
            BlogTag.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询博客标签列表
 */
BlogTag.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogTag.table.refresh({query: queryData});
};

/**
 * 导出博客标签
 */
BlogTag.export = function () {
    window.open("/blogTag/export")
};

$(function () {
    var defaultColunms = BlogTag.initColumn();
    var table = new BSTable(BlogTag.id, "/blogTag/list", defaultColunms);
    table.setPaginationType("server");
    BlogTag.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogTag/import/' //上传接口,
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
