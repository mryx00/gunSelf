/**
 * 初始化部门详情对话框
 */


var DeptinfoInfoDlg = {
    deptinfoInfoData : {},
    validateFields:{
            deptName:{
                     validators: {
                       notEmpty: {message:'部门名称不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
DeptinfoInfoDlg.clearData = function() {
    this.deptinfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptinfoInfoDlg.set = function(key, val) {
    this.deptinfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeptinfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
DeptinfoInfoDlg.close = function() {
    parent.layer.close(window.parent.Deptinfo.layerIndex);
}

/**
 * 收集数据
 */
DeptinfoInfoDlg.collectData = function() {
    this
    .set('deptId')
    .set('deptName')
    .set('deptDesp')
    .set('isDelete');
}


 /**
  * 验证数据是否为空
  */
 DeptinfoInfoDlg.validate = function () {
     $('#deptinfoInfoForm').data("bootstrapValidator").resetForm();
     $('#deptinfoInfoForm').bootstrapValidator('validate');
     return $("#deptinfoInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
DeptinfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deptinfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.Deptinfo.table.refresh();
        DeptinfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptinfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
DeptinfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deptinfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.Deptinfo.table.refresh();
        DeptinfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deptinfoInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("deptinfoInfoForm", DeptinfoInfoDlg.validateFields)

});
