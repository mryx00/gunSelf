@/*
id : textarea的id
name : textarea的名称
value : textare的值
hidden: 隐藏
@*/
<div class="form-group"
     @if(isNotEmpty(hidden) && hidden == 'true'){
            style="display: none;"
     @}
>

    <label class="col-sm-3 control-label">${name}</label>

    <div class="col-sm-9">
        @if(isNotEmpty(value)){
        <textarea class="form-control" id="${id}" name="${id}">${value}</textarea>
        @}else{
        <textarea class="form-control" id="${id}" name="${id}">
        </textarea>
        @}
    </div>
</div>