function openTab(text, url, iconCls) {
    if ($("#tabs").tabs("exists", text)) {
        $("#tabs").tabs("select", text);
    } else {
        var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add", {
            title: text,
            iconCls: iconCls,
            closable: true,
            content: content
        });
    }
}

// 退出
function logout() {

    $.messager.confirm('来自Crm', '您想要退出该系统吗？', function (r) {
        if (r) {
            //  清除cookie
            $.removeCookie("userIdStr");
            window.location.href = ctx + '/index';
        }
    });

}

// 修改密码
function openPasswordModifyDialog() {
    $('#dlg').dialog('open');
}

function modifyPassword() {
    $('#fm').form('submit', {
        url: ctx + '/user/updateUserPwd',
        onSubmit: function () {
            return $(this).form('validate');	// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            //console.log(data);
            if(data.code == 200){
                $.messager.alert('来自crm', data.msg, 'info',function () {
                    //  清除cookie
                    $.removeCookie("userIdStr");
                    window.location.href = ctx + '/index';
                });
            }else{
                $.messager.alert('来自crm', data.msg, 'error');
            }
        }
    })
}

function closePasswordModifyDialog() {
    $('#dlg').dialog('close');
}