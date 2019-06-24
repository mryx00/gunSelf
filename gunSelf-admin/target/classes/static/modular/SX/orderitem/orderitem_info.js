/**
 * 初始化订单详情对话框
 */


var OrderitemInfoDlg = {
    orderitemInfoData : {},
    validateFields:{
            customerNo:{
                     validators: {
                       notEmpty: {message:'客户姓名不能为空'}
                     }
             },
            employeeNo:{
                     validators: {
                       notEmpty: {message:'下单员工不能为空'}
                     }
             },
            orderTime:{
                     validators: {
                       notEmpty: {message:'下单时间不能为空'}
                     }
             },
            orderState:{
                     validators: {
                       notEmpty: {message:'订单状态不能为空'}
                     }
             },
            protectNumber:{
                     validators: {
                       notEmpty: {message:'产品数量不能为空'}
                     }
             },
     }
};

/**
 * 清除数据
 */
OrderitemInfoDlg.clearData = function() {
    this.orderitemInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderitemInfoDlg.set = function(key, val) {
    this.orderitemInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OrderitemInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
OrderitemInfoDlg.close = function() {
    parent.layer.close(window.parent.Orderitem.layerIndex);
}

/**
 * 收集数据
 */
OrderitemInfoDlg.collectData = function() {
    this
    .set('orderNo')
    .set('customerNo')
    .set('employeeNo')
    .set('orderTime')
    .set('orderState')
    .set('protectNumber');
}


 /**
  * 验证数据是否为空
  */
 OrderitemInfoDlg.validate = function () {
     $('#orderitemInfoForm').data("bootstrapValidator").resetForm();
     $('#orderitemInfoForm').bootstrapValidator('validate');
     return $("#orderitemInfoForm").data('bootstrapValidator').isValid();
 }


/**
 * 提交添加
 */
OrderitemInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
       if (!this.validate()) {
               return;
               }


    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/orderitem/add", function(data){
        Feng.success("添加成功!");
        window.parent.Orderitem.table.refresh();
        OrderitemInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderitemInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
OrderitemInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/orderitem/update", function(data){
        Feng.success("修改成功!");
        window.parent.Orderitem.table.refresh();
        OrderitemInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderitemInfoData);
    ajax.start();
}

$(function() {
 Feng.initValidator("orderitemInfoForm", OrderitemInfoDlg.validateFields)

});
