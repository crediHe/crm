/* 查询 */
function querySaleChancesByParams() {
    $('#dg').datagrid('load', {
        customerName: $('#customerName').val(),
        state: $('#state').combobox('getValue'),
        devResult: $('#devResult').combobox('getValue'),
        createDate: $('#time').datebox('getValue')
    });
}

// 数据格式化
function formatState(value,row,index) {
    if(value==0){
        return "未分配";
    }
    if(value==1){
        return "已分配";
    }
}
function formatDev(value,row,index) {
    if(value==0){
        return "未开发";
    }
    if(value==1){
        return "开发中";
    }
    if(value==2){
        return "开发成功";
    }
    if(value==3){
        return "开发失败";
    }
}

// 在页面加载完再执行
$(function () {
    $('#dg').datagrid({
        rowStyler: function(index,row){
            // if(row.devResult==0){
            //     return 'background-color:blue;';
            // }else if(row.devResult==1){
            //     return 'background-color:yellow;';
            // }else if(row.devResult==2){
            //     return 'background-color:green;';
            // }else if(row.devResult==3){
            //     return 'background-color:red;';
            // }
            var devResult = row.devResult;
            if (devResult == 0) {
                return "background-color:#5bc0de;"; // 蓝色
            } else if (devResult == 1) {
                return "background-color:#f0ad4e;"; // 黄色
            } else if (devResult == 2) {
                return "background-color:#5cb85c;"; // 绿色
            } else if (devResult == 3) {
                return "background-color:#d9534f;"; // 红色
            }
        }
    });
});

// 添加（打开营销机会管理的添加功能）
function openAddSaleChacneDialog() {
    $('#dlg').dialog("open");
}

// 点击（打开营销机会管理的添加表单的）保存
function saveOrUpdateSaleChance() {
    $('#fm').form('submit', {
        url: ctx + '/saleChance/saveOrUpdateSaleChance',
        onSubmit: function () {
            return $(this).form('validate');
        },
        success: function (data) {
            data = JSON.parse(data);
            //console.log(data);
            if(data.code==200){
                $.messager.alert('来自Crm',data.msg,'info',function () {
                    // 关闭弹窗
                    $('#dlg').dialog("close");
                    // 刷新数据
                    $('#dg').datagrid('load');
                    //清空表单数据
                    $('#fm').form('clear');
                });
            }else{
                $.messager.alert('来自Crm',data.msg,'error');
            }
        }
    });

}

//更新
function openModifySaleChanceDialog() {
    /***
     * 1. 判断有没有勾选
     * 2. 判断有没有多选
     * 3. 回显数据到表格
     * */
    var rows = $('#dg').datagrid('getSelections');//获取勾选行
    //console.log(rows);
    if(rows.lenght==0){
        $.messager.alert('来自Crm','请选择1条数据进行更新');
        return;
    }
    if(rows.length==0){
        $.messager.alert('来自Crm','请选择1条数据进行更新');
        return;
    }
    if(rows.length>1){
        $.messager.alert('来自Crm','只能选择1条数据进行更新');
        return;
    }
    //console.log(rows[0]);
    $('#fm').form('load',rows[0]);//回显数据
    $('#dlg').dialog("open");
}
