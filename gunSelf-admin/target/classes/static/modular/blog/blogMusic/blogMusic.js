/**
 * 博客音乐管理初始化
 */
var BlogMusic = {
    id: "BlogMusicTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogMusic.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '音乐名称', field: 'musicName', visible: true, align: 'center', valign: 'middle'},
            {title: '账号', field: 'account', visible: true, align: 'center', valign: 'middle'},
            {title: '音乐地址', field: 'musicAddr', visible: true, align: 'center', valign: 'middle'},
            {title: '是否启用', field: 'isUse', visible: true, align: 'center', valign: 'middle',
                formatter: function (value, row, index) {
                    if (row.isUse === 0) {
                        return "否";
                    } else if (row.isUse === 1) {
                        return "是";
                    }
                }
            }
    ];
};

/**
 * 检查是否选中
 */
BlogMusic.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogMusic.ids[i] = selected[i].id;
          }
        BlogMusic.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加博客音乐
 */
BlogMusic.openAddBlogMusic = function () {
    var index = layer.open({
        type: 2,
        title: '添加博客音乐',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogMusic/blogMusic_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看博客音乐详情
 */
BlogMusic.openBlogMusicDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '博客音乐详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogMusic/blogMusic_update/' + BlogMusic.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除博客音乐
 */
BlogMusic.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogMusic/delete", function (data) {
            Feng.success("删除成功!");
            BlogMusic.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询博客音乐列表
 */
BlogMusic.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogMusic.table.refresh({query: queryData});
};

/**
 * 导出博客音乐
 */
BlogMusic.export = function () {
    window.open("/blogMusic/export")
};

$(function () {
    var defaultColunms = BlogMusic.initColumn();
    var table = new BSTable(BlogMusic.id, "/blogMusic/list", defaultColunms);
    table.setPaginationType("server");
    BlogMusic.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogMusic/import/' //上传接口,
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
