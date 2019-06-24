/**
 * 初始化详情对话框
 */


var HrArchiveInfoDlg = {
    hrArchiveInfoData : {},
    validateFields:{
            schemaId:{
                     validators: {
                       notEmpty: {message:'薪资方案ID不能为空'}
                     }
             },
            staffId:{
                     validators: {
                       notEmpty: {message:'对应账号不能为空'}
                     }
             },
            sex:{
                     validators: {
                       notEmpty: {message:'性别不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
HrArchiveInfoDlg.clearData = function() {
    this.hrArchiveInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HrArchiveInfoDlg.set = function(key, val) {
    this.hrArchiveInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HrArchiveInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
HrArchiveInfoDlg.close = function() {
    parent.layer.close(window.parent.HrArchive.layerIndex);
}

/**
 * 收集数据
 */
HrArchiveInfoDlg.collectData = function() {
    this
    .set('archiveId')
    .set('schemaId')
    .set('staffId')
    .set('employeeNo')
    .set('name')
    .set('sex')
    .set('departmentId')
    .set('position')
    .set('positionRank')
    .set('positionGrade')
    .set('executiveLevel')
    .set('birthDate')
    .set('age')
    .set('idCard')
    .set('origo')
    .set('residenceRegistration')
    .set('nationality')
    .set('politicalStatus')
    .set('marriageStatus')
    .set('graduateSchool')
    .set('idcardExpiryDate')
    .set('specialty')
    .set('eduQualification')
    .set('eduDegree')
    .set('foreignLanguageLevel')
    .set('email')
    .set('homeAddress')
    .set('mobile')
    .set('employmentType')
    .set('entryDate')
    .set('regularDate')
    .set('eduExperience')
    .set('resume')
    .set('archiveStatus')
    .set('leaveType')
    .set('leaveDate')
    .set('leaveReason')
    .set('isDel')
    .set('delTime')
    .set('delOperator')
    .set('operator')
    .set('operateTime')
    .set('domainId')
    .set('descCnt');
}


 /**
  * 验证数据是否为空
  */
 HrArchiveInfoDlg.validate = function () {
     $('#hrArchiveInfoForm').data("bootstrapValidator").resetForm();
     $('#hrArchiveInfoForm').bootstrapValidator('validate');
     return $("#hrArchiveInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
HrArchiveInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/hrArchive/add", function(data){
        Feng.success("添加成功!");
        window.parent.HrArchive.table.refresh();
        HrArchiveInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.hrArchiveInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
HrArchiveInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/hrArchive/update", function(data){
        Feng.success("修改成功!");
        window.parent.HrArchive.table.refresh();
        HrArchiveInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.hrArchiveInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("hrArchiveInfoForm", HrArchiveInfoDlg.validateFields)

});
