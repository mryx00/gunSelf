/**
 * 初始化博客专题详情对话框
 */


var BlogTopicInfoDlg = {
    blogTopicInfoData : {},
    validateFields:{
            topicName:{
                     validators: {
                       notEmpty: {message:'专题名称不能为空'}
                     }
             },
            createTime:{
                     validators: {
                       notEmpty: {message:'创建时间不能为空'}
                     }
             },
            account:{
                     validators: {
                       notEmpty: {message:'账号不能为空'}
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
BlogTopicInfoDlg.clearData = function() {
    this.blogTopicInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogTopicInfoDlg.set = function(key, val) {
    this.blogTopicInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogTopicInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogTopicInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogTopic.layerIndex);
}

/**
 * 收集数据
 */
BlogTopicInfoDlg.collectData = function() {
    this
    .set('id')
    .set('topicName')
    .set('createTime')
    .set('account')
    .set('order');
}


 /**
  * 验证数据是否为空
  */
 BlogTopicInfoDlg.validate = function () {
     $('#blogTopicInfoForm').data("bootstrapValidator").resetForm();
     $('#blogTopicInfoForm').bootstrapValidator('validate');
     return $("#blogTopicInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogTopicInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogTopic/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogTopic.table.refresh();
        BlogTopicInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogTopicInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogTopicInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogTopic/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogTopic.table.refresh();
        BlogTopicInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogTopicInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogTopicInfoForm", BlogTopicInfoDlg.validateFields)

});
