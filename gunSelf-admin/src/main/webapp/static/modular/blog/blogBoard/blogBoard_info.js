/**
 * 初始化博客留言详情对话框
 */


var BlogBoardInfoDlg = {
    blogBoardInfoData : {},
    validateFields:{
            content:{
                     validators: {
                       notEmpty: {message:'留言内容不能为空'}
                     }
             },
            account:{
                     validators: {
                       notEmpty: {message:'用户名不能为空'}
                     }
             },
            blogAccount:{
                     validators: {
                       notEmpty: {message:'所属博客账户不能为空'}
                     }
             },
            creatTime:{
                     validators: {
                       notEmpty: {message:'发表时间不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogBoardInfoDlg.clearData = function() {
    this.blogBoardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogBoardInfoDlg.set = function(key, val) {
    this.blogBoardInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogBoardInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogBoardInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogBoard.layerIndex);
}

/**
 * 收集数据
 */
BlogBoardInfoDlg.collectData = function() {
    this
    .set('id')
    .set('content')
    .set('account')
    .set('blogAccount')
    .set('creatTime');
}


 /**
  * 验证数据是否为空
  */
 BlogBoardInfoDlg.validate = function () {
     $('#blogBoardInfoForm').data("bootstrapValidator").resetForm();
     $('#blogBoardInfoForm').bootstrapValidator('validate');
     return $("#blogBoardInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogBoardInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogBoard/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogBoard.table.refresh();
        BlogBoardInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogBoardInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogBoardInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogBoard/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogBoard.table.refresh();
        BlogBoardInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogBoardInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogBoardInfoForm", BlogBoardInfoDlg.validateFields)

});
