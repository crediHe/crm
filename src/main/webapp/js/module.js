// 格式化
function formateGrade(val) {
    if(val==0){
        return "根菜单";
    }
    if(val==1){
        return "一级菜单";
    }
    if(val==2){
        return "二级菜单";
    }
}

//查询
function queryModulesByParams() {
    $('#dg').datagrid('load',{
        moduleName: $('#moduleName').val(),
        parentId: $('#pid').val(),
        grade: $('#grade').combobox('getValue'),
        optValue: $('#optValue').val()
    });
}

//添加
function openAddModuleDailog() {
    openAddOrUpdateDlg('dlg','添加模块')
}

$(function () {
    $('#parentMenu').hide();

    $('#grade02').combobox({
        onSelect:function () {
            var grade = $('#grade02').combobox('getValue');

            if(grade==0){
                $('#parentMenu').hide();
            }else{
                $('#parentMenu').show();
                /* 加载模块 */
                loadModuleByGrade(grade-1);
            }
        }
    });


    function loadModuleByGrade(grade) {
        $('#parentId02').combobox({
            url:ctx+'/module/queryModuleByGrade?grade='+grade,
            valueField:'id',
            textField:'moduleName'
        });
    }
});