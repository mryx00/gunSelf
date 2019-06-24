/**
 * 博客相片管理初始化
 */
var BlogPhoto = {
    id: "BlogPhotoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogPhoto.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '相片名', field: 'photoName', visible: true, align: 'center', valign: 'middle'},
            {title: '相片预览', field: 'photoAddr', visible: true, align: 'center', valign: 'middle',formatter: function(value,row,index) {
                    if (row.avatar !== "") {
                        return '<img style="width: 60px;height: 60px" src="/pictures/' + row.photoAddr + '" class="img-rounded" >';
                    } else {
                        return null;
                    }
                }
                },
            {title: '相片地址', field: 'photoAddr', visible: true, align: 'center', valign: 'middle'},
            {title: '排序', field: 'order', visible: true, align: 'center', valign: 'middle'},
            {title: '用户', field: 'account', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogPhoto.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogPhoto.ids[i] = selected[i].id;
          }
        BlogPhoto.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加博客相片
 */
BlogPhoto.openAddBlogPhoto = function () {
    var index = layer.open({
        type: 2,
        title: '添加博客相片',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogPhoto/blogPhoto_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看博客相片详情
 */
BlogPhoto.openBlogPhotoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '博客相片详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogPhoto/blogPhoto_update/' + BlogPhoto.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除博客相片
 */
BlogPhoto.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogPhoto/delete", function (data) {
            Feng.success("删除成功!");
            BlogPhoto.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("ids",this.ids );
        ajax.start();
    }
};

/**
 * 查询博客相片列表
 */
BlogPhoto.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogPhoto.table.refresh({query: queryData});
};

/**
 * 导出博客相片
 */
BlogPhoto.export = function () {
    window.open("/blogPhoto/export")
};

$(function () {
    var defaultColunms = BlogPhoto.initColumn();
    var table = new BSTable(BlogPhoto.id, "/blogPhoto/list", defaultColunms);
    table.setPaginationType("server");
    BlogPhoto.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogPhoto/import/' //上传接口,
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
