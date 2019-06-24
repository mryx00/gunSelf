/**
 * 初始化博客内容详情对话框
 */


var BlogContentInfoDlg = {
    blogContentInfoData : {},
    validateFields:{
     }
};

/**
 * 清除数据
 */
BlogContentInfoDlg.clearData = function() {
    this.blogContentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogContentInfoDlg.set = function(key, val) {
    this.blogContentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BlogContentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BlogContentInfoDlg.close = function() {
    parent.layer.close(window.parent.BlogContent.layerIndex);
}

/**
 * 收集数据
 */
BlogContentInfoDlg.collectData = function() {
    this
    .set('id')
    .set('caption')
    .set('auther')
    .set('date')
    .set('content')
    .set('top')
    .set('pv')
    .set('topic');
};

var inputTags1 = new Array();
var inputTags2 = null;
layui.config({
    base: '/static/js/', 	}
).use(['inputTags'],function(){
    var inputTags = layui.inputTags;

    inputTags2 =inputTags.render({
        elem:'#inputTags',//定义输入框input对象
        content: [],//默认标签
        aldaBtn: false,//是否开启获取所有数据的按钮
        done: function(value){ //回车后的回调
            console.log(value);
            console.log(this.content);
            inputTags1 = this.content;
        }
    });
});

/**
 * 提交添加
 */
BlogContentInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    var inputTags = layui.inputTags;



    console.log(this.blogContentInfoData);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogContent/add", function(data){
        Feng.success("添加成功!");
        window.parent.BlogContent.table.refresh();
        BlogContentInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogContentInfoData);
    console.log(inputTags2.config.content);
    ajax.set("tags",inputTags2.config.content);
    ajax.start();
};

/**
 * 提交修改
 */
BlogContentInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    console.log(this.blogContentInfoData);
    var inputTags = layui.inputTags;



    console.log(this.blogContentInfoData);

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/blogContent/update", function(data){
        Feng.success("添加成功!");
        window.parent.BlogContent.table.refresh();
        BlogContentInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.blogContentInfoData);
    console.log(inputTags1);
    ajax.set("tags",inputTags1);
    ajax.start();
    //提交信息
    // var ajax = new $ax(Feng.ctxPath + "/blogContent/update", function(data){
    //     Feng.success("修改成功!");
    //     window.parent.BlogContent.table.refresh();
    //     BlogContentInfoDlg.close();
    // },function(data){
    //     Feng.error("修改失败!" + data.responseJSON.message + "!");
    // });
    // ajax.set(this.blogContentInfoData);
    // ajax.start();
}

$(function() {
 Feng.initValidator("blogContentInfoForm", BlogContentInfoDlg.validateFields)

});
