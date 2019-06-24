/**
 * 管理初始化
 */
var BlogTop = {
    id: "BlogTopTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogTop.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '文章Id', field: 'articleId', visible: false, align: 'center', valign: 'middle'},
            {title: '点赞人Id', field: 'tpPersonId', visible: false, align: 'center', valign: 'middle'},
            {title: '点赞时间', field: 'tpDate', visible: true, align: 'center', valign: 'middle'},
            {title: '文章标题', field: 'caption', visible: true, align: 'center', valign: 'middle'},
            {title: '点赞账户', field: 'account', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogTop.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogTop.ids[i] = selected[i].id;
          }
        BlogTop.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
BlogTop.openAddBlogTop = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogTop/blogTop_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
BlogTop.openBlogTopDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogTop/blogTop_update/' + BlogTop.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
BlogTop.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogTop/delete", function (data) {
            Feng.success("删除成功!");
            BlogTop.table.refresh();
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
BlogTop.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogTop.table.refresh({query: queryData});
};

/**
 * 导出
 */
BlogTop.export = function () {
    window.open("/blogTop/export")
};

$(function () {
    var defaultColunms = BlogTop.initColumn();
    var table = new BSTable(BlogTop.id, "/blogTop/list", defaultColunms);
    table.setPaginationType("server");
    BlogTop.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogTop/import/' //上传接口,
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
