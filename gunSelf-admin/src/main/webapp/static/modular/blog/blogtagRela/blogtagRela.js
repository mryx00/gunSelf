/**
 * 博客标签关联管理初始化
 */
var BlogtagRela = {
    id: "BlogtagRelaTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogtagRela.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '博客主键', field: 'blogId', visible: true, align: 'center', valign: 'middle'},
            {title: '标签主键', field: 'tagId', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogtagRela.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogtagRela.ids[i] = selected[i].id;
          }
        BlogtagRela.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加博客标签关联
 */
BlogtagRela.openAddBlogtagRela = function () {
    var index = layer.open({
        type: 2,
        title: '添加博客标签关联',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogtagRela/blogtagRela_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看博客标签关联详情
 */
BlogtagRela.openBlogtagRelaDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '博客标签关联详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogtagRela/blogtagRela_update/' + BlogtagRela.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除博客标签关联
 */
BlogtagRela.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogtagRela/delete", function (data) {
            Feng.success("删除成功!");
            BlogtagRela.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询博客标签关联列表
 */
BlogtagRela.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogtagRela.table.refresh({query: queryData});
};

/**
 * 导出博客标签关联
 */
BlogtagRela.export = function () {
    window.open("/blogtagRela/export")
};

$(function () {
    var defaultColunms = BlogtagRela.initColumn();
    var table = new BSTable(BlogtagRela.id, "/blogtagRela/list", defaultColunms);
    table.setPaginationType("server");
    BlogtagRela.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogtagRela/import/' //上传接口,
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
