/**
 * 初始化详情对话框
 */


var PeopleInfoDlg = {
    peopleInfoData : {},
    validateFields:{
     }
};

/**
 * 清除数据
 */
PeopleInfoDlg.clearData = function() {
    this.peopleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PeopleInfoDlg.set = function(key, val) {
    this.peopleInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PeopleInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PeopleInfoDlg.close = function() {
    parent.layer.close(window.parent.People.layerIndex);
}

/**
 * 收集数据
 */
PeopleInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name');
}




/**
 * 提交添加
 */
PeopleInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/people/add", function(data){
        Feng.success("添加成功!");
        window.parent.People.table.refresh();
        PeopleInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.peopleInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PeopleInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/people/update", function(data){
        Feng.success("修改成功!");
        window.parent.People.table.refresh();
        PeopleInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.peopleInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("peopleInfoForm", PeopleInfoDlg.validateFields)

});
