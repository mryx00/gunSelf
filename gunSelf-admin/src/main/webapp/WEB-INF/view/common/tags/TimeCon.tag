@/*
    时间查询条件标签的参数说明:

    name : 查询条件的名称
    id : 查询内容的input框id
    isTime : 日期是否带有小时和分钟(true/false)
    pattern : 日期的正则表达式(例如:"YYYY-MM-DD")
@*/
<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle"
                style="background-color: rgb(251,251,251)"
                type="button">${name}
        </button>
    </div>
    <input type="text" id="${id}" class="form-control layer-date"
           />
    <script>
        laydate.render({elem: '#${id}',type: 'datetime',trigger: 'click'});
    </script>
</div>