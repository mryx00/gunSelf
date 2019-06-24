/**
 * 初始化详情对话框
 */


var BlogCommentInfoDlg = {
    blogCommentInfoData : {},
    validateFields:{
     }
};

/**
 * 清除数据
 */
BlogCommentInfoDlg.clearData = function() {
    this.blogCommentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogCommentInfoDlg.set = function(key, val) {
    this.blogCommentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogCommentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogCommentInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogComment.layerIndex);
}

/**
 * 收集数据
 */
BlogCommentInfoDlg.collectData = function() {
    this
    .set('id')
    .set('articleId')
    .set('ctPersonId')
    .set('ctContent')
    .set('ctDate');
}




/**
 * 提交添加
 */
BlogCommentInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogComment/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogComment.table.refresh();
        BlogCommentInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogCommentInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogCommentInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogComment/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogComment.table.refresh();
        BlogCommentInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogCommentInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogCommentInfoForm", BlogCommentInfoDlg.validateFields)

});
