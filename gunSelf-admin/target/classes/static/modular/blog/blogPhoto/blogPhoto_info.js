/**
 * 初始化博客相片详情对话框
 */


var BlogPhotoInfoDlg = {
    blogPhotoInfoData : {},
    validateFields:{
            photoName:{
                     validators: {
                       notEmpty: {message:'相片名不能为空'}
                     }
             },
            order:{
                     validators: {
                       notEmpty: {message:'排序不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogPhotoInfoDlg.clearData = function() {
    this.blogPhotoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogPhotoInfoDlg.set = function(key, val) {
    this.blogPhotoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogPhotoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogPhotoInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogPhoto.layerIndex);
}

/**
 * 收集数据
 */
BlogPhotoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('photoName')
    .set('photoAddr')
    .set('order')
    .set('account');
}


 /**
  * 验证数据是否为空
  */
 BlogPhotoInfoDlg.validate = function () {
     $('#blogPhotoInfoForm').data("bootstrapValidator").resetForm();
     $('#blogPhotoInfoForm').bootstrapValidator('validate');
     return $("#blogPhotoInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogPhotoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogPhoto/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogPhoto.table.refresh();
        BlogPhotoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogPhotoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogPhotoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogPhoto/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogPhoto.table.refresh();
        BlogPhotoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogPhotoInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogPhotoInfoForm", BlogPhotoInfoDlg.validateFields)

});
