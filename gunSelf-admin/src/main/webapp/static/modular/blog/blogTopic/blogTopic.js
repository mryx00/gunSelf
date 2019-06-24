/**
 * 博客专题管理初始化
 */
var BlogTopic = {
    id: "BlogTopicTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogTopic.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '专题名称', field: 'topicName', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '账号', field: 'account', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'order', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogTopic.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogTopic.ids[i] = selected[i].id;
          }
        BlogTopic.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加博客专题
 */
BlogTopic.openAddBlogTopic = function () {
    var index = layer.open({
        type: 2,
        title: '添加博客专题',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogTopic/blogTopic_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看博客专题详情
 */
BlogTopic.openBlogTopicDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '博客专题详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogTopic/blogTopic_update/' + BlogTopic.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除博客专题
 */
BlogTopic.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogTopic/delete", function (data) {
            Feng.success("删除成功!");
            BlogTopic.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询博客专题列表
 */
BlogTopic.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogTopic.table.refresh({query: queryData});
};

/**
 * 导出博客专题
 */
BlogTopic.export = function () {
    window.open("/blogTopic/export")
};

$(function () {
    var defaultColunms = BlogTopic.initColumn();
    var table = new BSTable(BlogTopic.id, "/blogTopic/list", defaultColunms);
    table.setPaginationType("server");
    BlogTopic.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogTopic/import/' //上传接口,
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
