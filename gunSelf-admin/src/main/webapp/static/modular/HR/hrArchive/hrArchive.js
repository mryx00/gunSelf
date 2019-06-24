/**
 * 管理初始化
 */
var HrArchive = {
    id: "HrArchiveTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
HrArchive.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '档案ID', field: 'archiveId', visible: true, align: 'center', valign: 'middle'},
            {title: '薪资方案ID', field: 'schemaId', visible: true, align: 'center', valign: 'middle'},
            {title: '对应账号', field: 'staffId', visible: true, align: 'center', valign: 'middle'},
            {title: '工号', field: 'employeeNo', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '性别', field: 'sex', visible: true, align: 'center', valign: 'middle'},
            {title: '部门', field: 'departmentId', visible: true, align: 'center', valign: 'middle'},
            {title: '职务', field: 'position', visible: true, align: 'center', valign: 'middle'},
            {title: '职级', field: 'positionRank', visible: true, align: 'center', valign: 'middle'},
            {title: '职等', field: 'positionGrade', visible: true, align: 'center', valign: 'middle'},
            {title: '行政级别', field: 'executiveLevel', visible: true, align: 'center', valign: 'middle'},
            {title: '出生日期', field: 'birthDate', visible: true, align: 'center', valign: 'middle'},
            {title: '年龄', field: 'age', visible: true, align: 'center', valign: 'middle'},
            {title: '身份证号', field: 'idCard', visible: true, align: 'center', valign: 'middle'},
            {title: '籍贯', field: 'origo', visible: true, align: 'center', valign: 'middle'},
            {title: '户口所在', field: 'residenceRegistration', visible: true, align: 'center', valign: 'middle'},
            {title: '民族', field: 'nationality', visible: true, align: 'center', valign: 'middle'},
            {title: '政治面貌', field: 'politicalStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '婚姻状况', field: 'marriageStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '毕业院校', field: 'graduateSchool', visible: true, align: 'center', valign: 'middle'},
            {title: '身份证到期', field: 'idcardExpiryDate', visible: true, align: 'center', valign: 'middle'},
            {title: '专业', field: 'specialty', visible: true, align: 'center', valign: 'middle'},
            {title: '学历', field: 'eduQualification', visible: true, align: 'center', valign: 'middle'},
            {title: '学位', field: 'eduDegree', visible: true, align: 'center', valign: 'middle'},
            {title: '外语水平', field: 'foreignLanguageLevel', visible: true, align: 'center', valign: 'middle'},
            {title: '电子邮箱', field: 'email', visible: true, align: 'center', valign: 'middle'},
            {title: '家庭地址', field: 'homeAddress', visible: true, align: 'center', valign: 'middle'},
            {title: '移动电话', field: 'mobile', visible: true, align: 'center', valign: 'middle'},
            {title: '用工类型', field: 'employmentType', visible: true, align: 'center', valign: 'middle'},
            {title: '入职时间', field: 'entryDate', visible: true, align: 'center', valign: 'middle'},
            {title: '转正日期', field: 'regularDate', visible: true, align: 'center', valign: 'middle'},
            {title: '教育经历', field: 'eduExperience', visible: true, align: 'center', valign: 'middle'},
            {title: '个人简历', field: 'resume', visible: true, align: 'center', valign: 'middle'},
            {title: '档案状态', field: 'archiveStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '离职类别', field: 'leaveType', visible: true, align: 'center', valign: 'middle'},
            {title: '离职日期', field: 'leaveDate', visible: true, align: 'center', valign: 'middle'},
            {title: '离职原因', field: 'leaveReason', visible: true, align: 'center', valign: 'middle'},
            {title: '是否删除', field: 'isDel', visible: true, align: 'center', valign: 'middle'},
            {title: '删除时间', field: 'delTime', visible: true, align: 'center', valign: 'middle'},
            {title: '删除人', field: 'delOperator', visible: true, align: 'center', valign: 'middle'},
            {title: '操作人', field: 'operator', visible: true, align: 'center', valign: 'middle'},
            {title: '操作日期', field: 'operateTime', visible: true, align: 'center', valign: 'middle'},
            {title: '单位ID', field: 'domainId', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'descCnt', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
HrArchive.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        HrArchive.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
HrArchive.openAddHrArchive = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/hrArchive/hrArchive_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
HrArchive.openHrArchiveDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/hrArchive/hrArchive_update/' + HrArchive.seItem.archiveId
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
HrArchive.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/hrArchive/delete", function (data) {
            Feng.success("删除成功!");
            HrArchive.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("hrArchiveId",this.seItem.archiveId );
        ajax.start();
    }
};

/**
 * 查询列表
 */
HrArchive.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    HrArchive.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = HrArchive.initColumn();
    var table = new BSTable(HrArchive.id, "/hrArchive/list", defaultColunms);
    table.setPaginationType("server");
    HrArchive.table = table.init();
});
