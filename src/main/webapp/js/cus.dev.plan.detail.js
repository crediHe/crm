
$(function () {
    var sid = $('#saleChanceId').val();
    $('#dg').edatagrid({
        url: ctx + '/cusDevPlan/queryCusDevPlansByParams?sid='+sid,
        saveUrl: ctx + '/cusDevPlan/saveOrUpdateCusDevPlan?sid='+sid,
        updateUrl: ctx + '/cusDevPlan/saveOrUpdateCusDevPlan?sid='+sid
    });

    /* 执行判断 devResult
     * 如果: 0 1 显示  2 3 隐藏
      * */
    var devResult = $('#devResult').val();
    if(devResult==2 || devResult==3){
        // 1. 隐藏 工具条
        $('#toolbar').hide();
        // 2. 使表格变为不可编辑
        $('#dg').edatagrid("disableEditing")
    }
});

function addRow() {
    $('#dg').edatagrid("addRow")
}

function saveOrUpdateCusDevPlan() {
    $('#dg').edatagrid("saveRow")
}

function delCusDevPlan() {
    /***
     * 1. 获取用户选中数据
     * */
    var rows = $('#dg').datagrid('getSelections');//获取勾选行
    if(rows.length==0){
        $.messager.alert('来自Crm','请选择数据进行删除');
        return;
    }
    $.messager.confirm('来自Crm','确定要删除'+rows.length+'条数据?',function (r) {
        if(r){
            var ids = '';
            for(var i=0; i<rows.length; i++){
                ids += 'ids='+rows[i].id+'&';
            }
            $.ajax({
                url: ctx +'/cusDevPlan/deleteBatchCusDevPlan',
                data: ids,
                success:function (data) {
                    if(data.code==200){
                        $.messager.alert('来自Crm',data.msg,'info',function () {
                            // 刷新数据
                            $('#dg').edatagrid('load');
                        });
                    }else{
                        $.messager.alert('来自Crm',data.msg,'error');
                    }
                }
            });
        }
    });
}

function updateSaleChanceDevResult(devResult) {
    var sid = $('#saleChanceId').val();
    $.ajax({
        url: ctx + '/saleChance/updateDevResult',
        type: 'post',
        data:{devResult:devResult, sid:sid},
        success:function (data) {
            if(data.code==200){
                $.messager.alert('来自Crm',data.msg,'info',function () {
                    // 1. 隐藏 工具条
                    $('#toolbar').hide();
                    // 2. 使表格变为不可编辑
                    $('#dg').edatagrid("disableEditing")
                });
            }else{
                $.messager.alert('来自Crm',data.msg,'error');
            }
        }
    });
}