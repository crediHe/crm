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