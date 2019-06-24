/**
 * 初始化详情对话框
 */


var BlogViewInfoDlg = {
    blogViewInfoData : {},
    validateFields:{
            viewPersonId:{
                     validators: {
                       notEmpty: {message:'浏览人id不能为空'}
                     }
             },
            articleId:{
                     validators: {
                       notEmpty: {message:'文章Id不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogViewInfoDlg.clearData = function() {
    this.blogViewInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogViewInfoDlg.set = function(key, val) {
    this.blogViewInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogViewInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogViewInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogView.layerIndex);
}

/**
 * 收集数据
 */
BlogViewInfoDlg.collectData = function() {
    this
    .set('id')
    .set('viewPersonId')
    .set('articleId')
    .set('viewDate');
}


 /**
  * 验证数据是否为空
  */
 BlogViewInfoDlg.validate = function () {
     $('#blogViewInfoForm').data("bootstrapValidator").resetForm();
     $('#blogViewInfoForm').bootstrapValidator('validate');
     return $("#blogViewInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogViewInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogView/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogView.table.refresh();
        BlogViewInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogViewInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogViewInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogView/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogView.table.refresh();
        BlogViewInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogViewInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogViewInfoForm", BlogViewInfoDlg.validateFields)

});
