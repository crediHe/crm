function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

/* 退出 */
function logout() {
    /***
     * 1. 清除cookie
     * 2. 跳转到登陆页
     * */
    // easyUI的easyUI Confirm 确认框
    $.messager.confirm('来自Crm', '您想要退出该系统吗？', function(r){
        if (r){
            // 退出操作;
            $.removeCookie("userIdStr");
            $.removeCookie("userName");
            $.removeCookie("realName");

            //跳转登陆页
            window.location.href = ctx +'/index';
        }
    });

}

//修改密码弹出窗
function openPasswordModifyDialog(){
    $('#dlg').dialog('open');
}

//修改密码
function modifyPassword() {
    $('#fm').form('submit', {
        url: ctx + "/user/updateUserPwd",
        onSubmit: function () {
            return $(this).form('validate');// 返回false终止表单提交
        },
        success: function (data) {
            data = JSON.parse(data);
            if(data.code == 200){
                /***
                 * 1. 成功清除cookie跳转登陆页
                 * */
                $.messager.alert('来自Crm',data.msg,'info',function () {
                    // 退出操作;
                    $.removeCookie("userIdStr");
                    $.removeCookie("userName");
                    $.removeCookie("realName");

                    //跳转登陆页
                    window.location.href = ctx + '/index';
                });
            }else{
                $.messager.alert('来自Crm',data.msg,'error');
            }
        }
    });
}
