/**
 * 类别管理初始化
 */
var ProductCategory = {
    id: "ProductCategoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ProductCategory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '编号', field: 'categoryNo', visible: true, align: 'center', valign: 'middle'},
            {title: '类别名称', field: 'categoryName', visible: true, align: 'center', valign: 'middle'},
            {title: '类别备注', field: 'description', visible: true, align: 'center', valign: 'middle'},
            {title: '是否下架', field: 'isDelete', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ProductCategory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ProductCategory.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加类别
 */
ProductCategory.openAddProductCategory = function () {
    var index = layer.open({
        type: 2,
        title: '添加类别',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/productCategory/productCategory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看类别详情
 */
ProductCategory.openProductCategoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '类别详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/productCategory/productCategory_update/' + ProductCategory.seItem.categoryNo
        });
        this.layerIndex = index;
    }
};

/**
 * 删除类别
 */
ProductCategory.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/productCategory/delete", function (data) {
            Feng.success("删除成功!");
            ProductCategory.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("productCategoryId",this.seItem.categoryNo );
        ajax.start();
    }
};

/**
 * 下架产品类别
 */
ProductCategory.remove = function () {
    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/productCategory/remove", function (data) {
                if (data.code ==500){
                    Feng.info(data.message);
                }else{
                    Feng.success("下架成功!");
                }
                ProductCategory.table.refresh();
            }, function (data) {
                Feng.error("下架失败!" + data.responseJSON.message + "!");
            });
            console.log(ProductCategory.seItem);
            ajax.set("categoryNo",ProductCategory.seItem.categoryNo );
            ajax.start();
        }
    }
    Feng.confirm("是否下架" + ProductCategory.seItem.categoryName + "?",operation);
};

/**
 * 恢复的产品类别
 */
ProductCategory.recovery = function () {
    if (this.check()) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/productCategory/recovery", function (data) {
                if (data.code ==500){
                    Feng.info(data.message);
                }else{
                    Feng.success("恢复成功!");
                }
                ProductCategory.table.refresh();
            }, function (data) {
                Feng.error("恢复失败!" + data.responseJSON.message + "!");
            });
            console.log(ProductCategory.seItem);
            ajax.set("categoryNo",ProductCategory.seItem.categoryNo );
            ajax.start();
        }
    }
    Feng.confirm("是否恢复" + ProductCategory.seItem.categoryName + "?",operation);
};
/**
 * 查询类别列表
 */
ProductCategory.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['isDelete'] = $("#isDelete").val();

    ProductCategory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ProductCategory.initColumn();
    var table = new BSTable(ProductCategory.id, "/productCategory/list", defaultColunms);
    table.setPaginationType("server");
    ProductCategory.table = table.init();
});
