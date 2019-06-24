@/*
    名称查询条件标签的参数说明:

    name : 查询条件的名称
    id : 查询内容的input框id
@*/
<div class="layui-form-item">
        <label class="layui-form-label">${name}</label>
    <div class="layui-input-block">
    <input type="text" class="layui-input" id="${id}" placeholder="${placeholder!}" />
    </div>
</div>