/**
 * 初始化部门详情对话框
 */
var EditFieldDlg = {

};

/**
 * 关闭此对话框
 */
EditFieldDlg.close = function() {
    parent.layer.close(window.parent.Code.layerIndex);
};

/**
 * 提交
 */
EditFieldDlg.addSubmit = function() {

    var ajax = new $ax(Feng.ctxPath + "/code/test", function(data){
        Feng.success("成功!");
        EditFieldDlg.close();
    },function(data){
        Feng.error("失败!" + data.responseJSON.message + "!");
    });
    ajax.data= $('#form1').serialize(),
    ajax.start();
}
