/**
 * 管理初始化
 */
var BlogComment = {
    id: "BlogCommentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BlogComment.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '文章Id', field: 'articleId', visible: false, align: 'center', valign: 'middle'},
            {title: '评论人', field: 'ctPersonId', visible: false, align: 'center', valign: 'middle'},
            {title: '文章标题', field: 'caption', visible: true, align: 'center', valign: 'middle'},
            {title: '评论人', field: 'account', visible: true, align: 'center', valign: 'middle'},
            {title: '评论内容', field: 'ctContent', visible: true, align: 'center', valign: 'middle'},
            {title: '评论时间', field: 'ctDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogComment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BlogComment.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
BlogComment.openAddBlogComment = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogComment/blogComment_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
BlogComment.openBlogCommentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogComment/blogComment_update/' + BlogComment.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
BlogComment.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogComment/delete", function (data) {
            Feng.success("删除成功!");
            BlogComment.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("blogCommentId",this.seItem.id );
        ajax.start();
    }
};

/**
 * 查询列表
 */
BlogComment.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogComment.table.refresh({query: queryData});
    console.log('data',BlogComment.table.getData());
};

$(function () {
    var defaultColunms = BlogComment.initColumn();
    var table = new BSTable(BlogComment.id, "/blogComment/list", defaultColunms);

    table.setPaginationType("server");
    BlogComment.table = table.init();




});
