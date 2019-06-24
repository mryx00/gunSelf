/**
 * 初始化详情对话框
 */


var BlogTopInfoDlg = {
    blogTopInfoData : {},
    validateFields:{
            articleId:{
                     validators: {
                       notEmpty: {message:'文章Id不能为空'}
                     }
             },
            tpPersonId:{
                     validators: {
                       notEmpty: {message:'点赞人Id不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogTopInfoDlg.clearData = function() {
    this.blogTopInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogTopInfoDlg.set = function(key, val) {
    this.blogTopInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogTopInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogTopInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogTop.layerIndex);
}

/**
 * 收集数据
 */
BlogTopInfoDlg.collectData = function() {
    this
    .set('id')
    .set('articleId')
    .set('tpPersonId')
    .set('tpDate');
}


 /**
  * 验证数据是否为空
  */
 BlogTopInfoDlg.validate = function () {
     $('#blogTopInfoForm').data("bootstrapValidator").resetForm();
     $('#blogTopInfoForm').bootstrapValidator('validate');
     return $("#blogTopInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogTopInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogTop/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogTop.table.refresh();
        BlogTopInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogTopInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogTopInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogTop/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogTop.table.refresh();
        BlogTopInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogTopInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogTopInfoForm", BlogTopInfoDlg.validateFields)

});
