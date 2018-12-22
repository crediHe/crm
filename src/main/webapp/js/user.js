// 查询
function queryUsersByParams() {
    $('#dg').datagrid('load',{
        userName: $('#userName').val(),
        email: $('#email').val(),
        phone: $('#phone').val()
    })
}

function openAddUserDailog() {
    openAddOrUpdateDlg('dlg','添加用户')
}

function saveOrUpdateUser() {
    saveOrUpdateData('fm',ctx+'/user/saveOrUpdateUser','dlg',queryUsersByParams);
}

function openModifyUserDialog () {
    openModifyDialog('dg','fm','dlg','更新用户')
}

$(function () {
    $('#dlg').dialog({
        "onClose":function () {
            // 清空表单数据
            $('#fm').form('clear');
        }
    })
});