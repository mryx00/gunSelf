/**
 * 博客内容管理初始化
 */
var BlogContent = {
    id: "BlogContentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BlogContent.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '标题', field: 'caption', visible: true, align: 'center', valign: 'middle'},
            {title: '作者', field: 'auther', visible: true, align: 'center', valign: 'middle'},
            {title: '发表日期', field: 'date', visible: true, align: 'center', valign: 'middle'},
            {title: '文章内容', field: 'content', visible: false, align: 'center', valign: 'middle'},
            {title: '点赞数', field: 'topSum', visible: true, align: 'center', valign: 'middle'},
            {title: '评论数', field: 'commentSum', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogContent.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BlogContent.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加博客内容
 */
BlogContent.openAddBlogContent = function () {
    var index = layer.open({
        type: 2,
        title: '添加博客内容',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogContent/blogContent_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看博客内容详情
 */
BlogContent.openBlogContentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '博客内容详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogContent/blogContent_update/' + BlogContent.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除博客内容
 */
BlogContent.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogContent/delete", function (data) {
            Feng.success("删除成功!");
            BlogContent.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("blogContentId",this.seItem.id );
        ajax.start();
    }
};

/**
 * 查询博客内容列表
 */
BlogContent.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogContent.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BlogContent.initColumn();
    var table = new BSTable(BlogContent.id, "/blogContent/list", defaultColunms);
    table.setPaginationType("server");
    BlogContent.table = table.init();
});
