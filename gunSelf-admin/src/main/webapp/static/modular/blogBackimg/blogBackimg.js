/**
 * 管理初始化
 */
var BlogBackimg = {
    id: "BlogBackimgTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    ids:new Array()//多选的id
};

/**
 * 初始化表格的列
 */
BlogBackimg.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '主键', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '图片名称', field: 'imgName', visible: true, align: 'center', valign: 'middle'},
        {
            title: '图片地址',
            field: 'imgAddr',
            visible: true,
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                if (row.avatar !== "") {
                    return '<img style="width: 60px;height: 60px" src="/pictures/' + row.photoAddr + '" class="img-rounded" >';
                } else {
                    return null;
                }
            }
        },
            {title: '是否启用', field: 'isUse', visible: true, align: 'center', valign: 'middle'},
            {title: '所属页面', field: 'belong', visible: true, align: 'center', valign: 'middle'},
            {title: '博客用户', field: 'blogAccout', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BlogBackimg.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
         for (var i = 0;i<selected.length;i++){
            BlogBackimg.ids[i] = selected[i].id;
          }
        BlogBackimg.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
BlogBackimg.openAddBlogBackimg = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/blogBackimg/blogBackimg_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
BlogBackimg.openBlogBackimgDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/blogBackimg/blogBackimg_update/' + BlogBackimg.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
BlogBackimg.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/blogBackimg/delete", function (data) {
            Feng.success("删除成功!");
            BlogBackimg.table.refresh();
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
BlogBackimg.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BlogBackimg.table.refresh({query: queryData});
};

/**
 * 导出
 */
BlogBackimg.export = function () {
    window.open("/blogBackimg/export")
};

$(function () {
    var defaultColunms = BlogBackimg.initColumn();
    var table = new BSTable(BlogBackimg.id, "/blogBackimg/list", defaultColunms);
    table.setPaginationType("server");
    BlogBackimg.table = table.init();

    layui.use('upload', function(){
            var upload = layui.upload;

            //执行实例
            var uploadInst = upload.render({
                elem: '#upload' //绑定元素
                ,url: '/blogBackimg/import/' //上传接口,
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
