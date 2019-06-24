/**
 * 初始化详情对话框
 */


var EmpinfoInfoDlg = {
    empinfoInfoData : {},
    validateFields:{
            empName:{
                     validators: {
                       notEmpty: {message:'员工姓名不能为空'}
                     }
             },
            empSex:{
                     validators: {
                       notEmpty: {message:'性别不能为空'}
                     }
             },
            empAge:{
                     validators: {
                       notEmpty: {message:'年龄不能为空'}
                     }
             },
            empDate:{
                     validators: {
                       notEmpty: {message:'入职日期不能为空'}
                     }
             },
            deptId:{
                     validators: {
                       notEmpty: {message:'部门不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
EmpinfoInfoDlg.clearData = function() {
    this.empinfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EmpinfoInfoDlg.set = function(key, val) {
    this.empinfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
EmpinfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
EmpinfoInfoDlg.close = function() {
    parent.layer.close(window.parent.Empinfo.layerIndex);
}

/**
 * 收集数据
 */
EmpinfoInfoDlg.collectData = function() {
    this
    .set('empNo')
    .set('empName')
    .set('empSex')
    .set('empAge')
    .set('empDate')
    .set('deptId');
}


 /**
  * 验证数据是否为空
  */
 EmpinfoInfoDlg.validate = function () {
     $('#empinfoInfoForm').data("bootstrapValidator").resetForm();
     $('#empinfoInfoForm').bootstrapValidator('validate');
     return $("#empinfoInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
EmpinfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/empinfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.Empinfo.table.refresh();
        EmpinfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.empinfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
EmpinfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/empinfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.Empinfo.table.refresh();
        EmpinfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.empinfoInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("empinfoInfoForm", EmpinfoInfoDlg.validateFields)

});
