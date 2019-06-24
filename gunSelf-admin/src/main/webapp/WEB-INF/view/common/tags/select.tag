@/*
    select标签中各个参数的说明:
    name : select的名称
    id : select的id
    underline : 是否带分割线
    selectVal:下拉框数组
    value:下拉框的值
    selectKey:下拉框中key的值
    selectValue:下拉框中Value的值
    dictName:字典名称
    lay-verify:验证规则
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <select class="form-control" id="${id}" name="${id}"
                @if(isNotEmpty(verify)){
                    lay-verify="${verify}" lay-verType="tips"
                @}
        >
            ${tagBody!}
                @if(isNotEmpty(dictName)){
                <option  value="">请选择</option>
                @for(entry in dictUtil.getDictByKey(dictName)){
                    @if(isNotEmpty(value)&&(entry.value == value)){
                    <option  value="${entry.value}" selected>${entry.key}</option>
                    @}
                    @else{
                    <option  value="${entry.value}">${entry.key}</option>
                    @}
                @}
                @}
                @if(isNotEmpty(selectVal)){
                <option  value="">请选择</option>
                @for(entry in selectVal){
                @if(isNotEmpty(value)&&(entry[selectValue] == parseInt(value))){
                <option  value="${entry[selectValue]}" selected>${entry[selectKey]}</option>
                @}
                @else{
                <option  value="${entry[selectValue]}">${entry[selectKey]}</option>
                @}
                @}
                @}

        </select>
        @if(isNotEmpty(hidden)){
            <input class="form-control" type="hidden" id="${hidden}" value="${hiddenValue!}">
        @}
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


