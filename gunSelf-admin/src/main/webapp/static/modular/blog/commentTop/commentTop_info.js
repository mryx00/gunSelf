/**
 * 初始化评论点赞详情对话框
 */


var CommentTopInfoDlg = {
    commentTopInfoData : {},
    validateFields:{
            id:{
                     validators: {
                       notEmpty: {message:'主键不能为空'}
                     }
             },
            commentId:{
                     validators: {
                       notEmpty: {message:'评论id不能为空'}
                     }
             },
            tpPersonId:{
                     validators: {
                       notEmpty: {message:'点赞人id不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
CommentTopInfoDlg.clearData = function() {
    this.commentTopInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CommentTopInfoDlg.set = function(key, val) {
    this.commentTopInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CommentTopInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CommentTopInfoDlg.close = function() {
    parent.layer.close(window.parent.CommentTop.layerIndex);
}

/**
 * 收集数据
 */
CommentTopInfoDlg.collectData = function() {
    this
    .set('id')
    .set('commentId')
    .set('tpPersonId')
    .set('tpDate');
}


 /**
  * 验证数据是否为空
  */
 CommentTopInfoDlg.validate = function () {
     $('#commentTopInfoForm').data("bootstrapValidator").resetForm();
     $('#commentTopInfoForm').bootstrapValidator('validate');
     return $("#commentTopInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
CommentTopInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/commentTop/add", function(data){
        Feng.success("添加成功!");
        window.parent.CommentTop.table.refresh();
        CommentTopInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.commentTopInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CommentTopInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/commentTop/update", function(data){
        Feng.success("修改成功!");
        window.parent.CommentTop.table.refresh();
        CommentTopInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.commentTopInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("commentTopInfoForm", CommentTopInfoDlg.validateFields)

});
