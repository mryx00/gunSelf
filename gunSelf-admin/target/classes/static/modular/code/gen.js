/**
 * 初始化
 */
var Code = {
    ztreeInstance: null,
    tableName: "",
    submitData: {},
    switchs: {},
    layerIndex: -1
};

var flag = true;

/**
 * 选择table的事件
 */
Code.selectTable = function (tableName) {

    SelectList.clearSelect("templateList");
    Code.switchs = {};

    if (SelectList.singelSelect("tableList", "tableName", tableName) == true) {
        Code.tableName = tableName;
        Code.setTableName(tableName);
    } else {
        Code.tableName = "";
    }
};

/**
 * 选择模板的事件
 */
Code.selectTemplate = function (templateKey) {
    if (Code.tableName != "") {
        if (SelectList.mutiSelect("templateList", "key", templateKey) == true) {
            Code.switchs[templateKey] = true;
        } else {
            Code.switchs[templateKey] = false;
        }
    } else {
        Feng.info("请先选择表");
    }
};
/**
 * 选择所有模版的事件
 *
 */
Code.selectAllTemplate = function () {
    console.log(flag);
    if (Code.tableName != "") {
        $("#templateList").find("a").each(function () {
            if (flag===true&&!$(this).hasClass('active')) {
                $(this).addClass('active');
                Code.switchs[$(this).attr('key')] = true;

            } else {
                if (flag===false&&$(this).hasClass('active')) {
                    $(this).removeClass('active');
                    Code.switchs[$(this).attr('key')] = false;
                }
                }
        }
        )
        flag = !flag;
    }else{
            Feng.info("请先选择表");
    }
};

/**
 * 跳转到字段设置界面
 */
Code.tableFieldSetting = function () {
    if (Code.tableName != "") {
        console.log(Code.tableName);

        var index = layer.open({
            type: 2,
            title: Code.tableName+'字段设置',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/code/tableFieldSetting/'+Code.tableName,
        });
        this.layerIndex = index;

    }else {
        Feng.info("请先选择表");
    }
};


/**
 * 点击生成
 */
Code.generate = function () {
    Code.submitData = {};
    Code.submitData.tableName = Code.tableName;
    this.set('projectPath').set('author').set('projectPackage').set('corePackage').set('ignoreTabelPrefix').set('bizName').set('moduleName').set('parentMenuName');
    var baseAjax = Feng.baseAjax("/code/generate", "生成代码");

    for (var item in Code.switchs) {
        Code.submitData[item] = Code.switchs[item];
    }

    baseAjax.setData(Code.submitData);
    baseAjax.start();
};

/**
 * 设置表名称
 */
Code.setTableName = function (tableName) {
    var preSize = $("#ignoreTabelPrefix").val().length;
    $("#tableName").val(tableName);
    $("#className").val(Feng.underLineToCamel(tableName.substring(preSize)));
};

/**
 * 点击父级编号input框时
 */
Code.onClickDept = function (e, treeId, treeNode) {
    $("#parentMenuName").attr("value", Code.ztreeInstance.getSelectedVal());
};

/**
 * 显示父级菜单选择的树
 */
Code.showMenuSelectTree = function () {
    Feng.showInputTree("parentMenuName", "pcodeTreeDiv", 15, 34);
};

$(function () {
    var ztree = new $ZTree("pcodeTree", "/menu/selectMenuTreeList");
    ztree.bindOnClick(Code.onClickDept);
    ztree.init();
    Code.ztreeInstance = ztree;
    $("#pcodeTree").css('width',$("#parentMenuName").css('width'));
});

Code.set = function (key, val) {
    Code.submitData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};