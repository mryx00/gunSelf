/**
 * 初始化类别详情对话框
 */


var ProductCategoryInfoDlg = {
    productCategoryInfoData : {},
    validateFields:{
            categoryName:{
                     validators: {
                       notEmpty: {message:'类别名称不能为空'}
                     }
             },
            description:{
                     validators: {
                       notEmpty: {message:'类别备注不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
ProductCategoryInfoDlg.clearData = function() {
    this.productCategoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductCategoryInfoDlg.set = function(key, val) {
    this.productCategoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ProductCategoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ProductCategoryInfoDlg.close = function() {
    parent.layer.close(window.parent.ProductCategory.layerIndex);
}

/**
 * 收集数据
 */
ProductCategoryInfoDlg.collectData = function() {
    this
    .set('categoryNo')
    .set('categoryName')
    .set('description')
    .set('isDelete');
}


 /**
  * 验证数据是否为空
  */
 ProductCategoryInfoDlg.validate = function () {
     $('#productCategoryInfoForm').data("bootstrapValidator").resetForm();
     $('#productCategoryInfoForm').bootstrapValidator('validate');
     return $("#productCategoryInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
ProductCategoryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productCategory/add", function(data){
        Feng.success("添加成功!");
        window.parent.ProductCategory.table.refresh();
        ProductCategoryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productCategoryInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ProductCategoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/productCategory/update", function(data){
        Feng.success("修改成功!");
        window.parent.ProductCategory.table.refresh();
        ProductCategoryInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.productCategoryInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("productCategoryInfoForm", ProductCategoryInfoDlg.validateFields)

});
