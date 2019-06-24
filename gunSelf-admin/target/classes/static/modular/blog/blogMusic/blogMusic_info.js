/**
 * 初始化博客音乐详情对话框
 */


var BlogMusicInfoDlg = {
    blogMusicInfoData : {},
    validateFields:{
            musicName:{
                     validators: {
                       notEmpty: {message:'音乐名称不能为空'}
                     }
             },
            account:{
                     validators: {
                       notEmpty: {message:'账号不能为空'}
                     }
             },
            musicAddr:{
                     validators: {
                       notEmpty: {message:'音乐地址不能为空'}
                     }
             },
            isUse:{
                     validators: {
                       notEmpty: {message:'是否启用不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
BlogMusicInfoDlg.clearData = function() {
    this.blogMusicInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogMusicInfoDlg.set = function(key, val) {
    this.blogMusicInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogMusicInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogMusicInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogMusic.layerIndex);
}

/**
 * 收集数据
 */
BlogMusicInfoDlg.collectData = function() {
    this
    .set('id')
    .set('musicName')
    .set('account')
    .set('musicAddr')
    .set('isUse');
}


 /**
  * 验证数据是否为空
  */
 BlogMusicInfoDlg.validate = function () {
     $('#blogMusicInfoForm').data("bootstrapValidator").resetForm();
     $('#blogMusicInfoForm').bootstrapValidator('validate');
     return $("#blogMusicInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
BlogMusicInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogMusic/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogMusic.table.refresh();
        BlogMusicInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogMusicInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BlogMusicInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogMusic/update", function(data){
        Feng.success("修改成功!");
        window.parent.BlogMusic.table.refresh();
        BlogMusicInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogMusicInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("blogMusicInfoForm", BlogMusicInfoDlg.validateFields)

});
