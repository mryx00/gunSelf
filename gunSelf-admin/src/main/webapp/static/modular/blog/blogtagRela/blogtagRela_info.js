/**
 * 初始化博客标签关联详情对话框
 */


var BlogtagRelaInfoDlg = {
    blogtagRelaInfoData : {},
    validateFields:{
            blogId:{
                     validators: {
                       notEmpty: {message:'博客主键不能为空'}
                     }
             },
            tagId:{
                     validators: {
                       notEmpty: {message:'标签主键不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogtagRelaInfoDlg.clearData = function() {
    this.blogtagRelaInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogtagRelaInfoDlg.set = function(key, val) {
    this.blogtagRelaInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogtagRelaInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogtagRelaInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogtagRela.layerIndex);
}

/**
 * 收集数据
 */
BlogtagRelaInfoDlg.collectData = function() {
    this
    .set('id')
    .set('blogId')
    .set('tagId');
}


 /**
  * 验证数据是否为空
  */
 BlogtagRelaInfoDlg.validate = function () {
     $('#blogtagRelaInfoForm').data("bootstrapValidator").resetForm();
     $('#blogtagRelaInfoForm').bootstrapValidator('validate');
     return $("#blogtagRelaInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogtagRelaInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogtagRela/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogtagRela.table.refresh();
        BlogtagRelaInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogtagRelaInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogtagRelaInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogtagRela/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogtagRela.table.refresh();
        BlogtagRelaInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogtagRelaInfoData);
    ajax.start();
};

$(function() {
 Feng.initValidator("blogtagRelaInfoForm", BlogtagRelaInfoDlg.validateFields)

});
