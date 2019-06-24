/**
 * 初始化博客背景详情对话框
 */


var BlogBackimgInfoDlg = {
    blogBackimgInfoData : {},
    validateFields:{
            imgName:{
                     validators: {
                       notEmpty: {message:'图片名称不能为空'}
                     }
             },
            imgAddr:{
                     validators: {
                       notEmpty: {message:'图片地址不能为空'}
                     }
             },
            isUse:{
                     validators: {
                       notEmpty: {message:'是否启用不能为空'}
                     }
             },
            belong:{
                     validators: {
                       notEmpty: {message:'所属页面不能为空'}
                     }
             },
            blogAccout:{
                     validators: {
                       notEmpty: {message:'博客用户不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogBackimgInfoDlg.clearData = function() {
    this.blogBackimgInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogBackimgInfoDlg.set = function(key, val) {
    this.blogBackimgInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogBackimgInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogBackimgInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogBackimg.layerIndex);
}

/**
 * 收集数据
 */
BlogBackimgInfoDlg.collectData = function() {
    this
    .set('id')
    .set('imgName')
    .set('imgAddr')
    .set('isUse')
    .set('belong')
    .set('blogAccout');
}


 /**
  * 验证数据是否为空
  */
 BlogBackimgInfoDlg.validate = function () {
     $('#blogBackimgInfoForm').data("bootstrapValidator").resetForm();
     $('#blogBackimgInfoForm').bootstrapValidator('validate');
     return $("#blogBackimgInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogBackimgInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogBackimg/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogBackimg.table.refresh();
        BlogBackimgInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogBackimgInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogBackimgInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogBackimg/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogBackimg.table.refresh();
        BlogBackimgInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogBackimgInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogBackimgInfoForm", BlogBackimgInfoDlg.validateFields)

});
