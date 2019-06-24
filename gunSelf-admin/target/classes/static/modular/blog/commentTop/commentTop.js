/**
 * 评论点赞管理初始化
 */
var CommentTop = {
    id: "CommentTopTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
CommentTop.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '评论id', field: 'commentId', visible: false, align: 'center', valign: 'middle'},
            {title: '点赞人id', field: 'tpPersonId', visible: false, align: 'center', valign: 'middle'},
            {title: '评论内容', field: 'ctContent', visible: true, align: 'center', valign: 'middle'},
            {title: '文章标题', field: 'caption', visible: true, align: 'center', valign: 'middle'},
            {title: '点赞人', field: 'account', visible: true, align: 'center', valign: 'middle'},
            {title: '点赞日期', field: 'tpDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
CommentTop.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            CommentTop.ids[i] = selected[i].id;
          }
        CommentTop.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加评论点赞
 */
CommentTop.openAddCommentTop = function () {
    var index = layer.open({
        type: 2,
        title: '添加评论点赞',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/commentTop/commentTop_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看评论点赞详情
 */
CommentTop.openCommentTopDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '评论点赞详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/commentTop/commentTop_update/' + CommentTop.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除评论点赞
 */
CommentTop.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/commentTop/delete", function (data) {
            Feng.success("删除成功!");
            CommentTop.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询评论点赞列表
 */
CommentTop.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    CommentTop.table.refresh({query: queryData});
};

/**
 * 导出评论点赞
 */
CommentTop.export = function () {
    window.open("/commentTop/export")
};

$(function () {
    var defaultColunms = CommentTop.initColumn();
    var table = new BSTable(CommentTop.id, "/commentTop/list", defaultColunms);
    table.setPaginationType("server");
    CommentTop.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/commentTop/import/' //上传接口,
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
