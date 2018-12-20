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