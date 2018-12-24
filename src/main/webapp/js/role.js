// 查询
function queryRolesByParams() {
    $('#dg').datagrid('load',{
        roleName: $('#roleName').val(),
        createDate: $('#time').datebox('getValue')
    });
}

//添加
function openAddRoleDailog() {
    openAddOrUpdateDlg("dlg","添加角色")
}
function saveOrUpdateRole() {
    saveOrUpdateData('fm',ctx +'/role/saveOrUpdateRole','dlg',queryRolesByParams)
}

//更新
function openModifyRoleDialog() {
    openModifyDialog('dg','fm','dlg','更新角色')
}

// 打开授权弹窗
function openRelationPermissionDialog() {
    var rows = $('#dg').datagrid('getSelections');//获取勾选行
    if(rows.length==0){
        $.messager.alert('来自Crm','请选择一个角色进行授权');
        return;
    }
    if(rows.length>1){
        $.messager.alert('来自Crm','只能选择一个角色进行授权');
        return;
    }


    /***
     * 1. 打开弹窗
     * 2. 回显模块树
     * */
    loadModule(rows[0].id);
}

var treeObj;
// 加载模块树
function loadModule(roleId) {
    // 设置隐藏域roleId
    $('#roleId').val(roleId);
    // 清空 moduleIds
    $('#moduleIds').val('');

    $.ajax({
        url: ctx + '/role/queryPermissionByRoleId?roleId='+roleId,
        type: 'post',
        success:function (data) {
            //console.log(data);

            // zTree配置
            var setting = {
                check: {
                    enable: true,
                    chkboxType: {"Y": "ps", "N": "ps"}
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            // 节点数据
            var zNodes = data;
            // 初始化zTree
            treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);

            //显示弹窗
            openAddOrUpdateDlg('permissionDlg','角色授权');

        }
    });
}
// zTree选中触发
function zTreeOnCheck() {
    var nodes = treeObj.getCheckedNodes(true);
    //console.log(nodes);
    var moduleIds = "";
    for(var i=0; i<nodes.length; i++){
        moduleIds +="moduleIds="+nodes[i].id+"&"
    }
    $('#moduleIds').val(moduleIds);
}


//授权
function doGrant () {
    $.ajax({
        url: ctx + '/permission/doGrant?'+$('#moduleIds').val(),
        type:'post',
        data:{
            roleId: $('#roleId').val()
        },
        success:function (data) {
            if(data.code==200){
                $.messager.alert('来自Crm',data.msg,'info',function () {
                    // 关闭弹窗
                    closeDlgData('permissionDlg');
                    // 刷新数据
                    $('#dg').datagrid('load');
                });
            }else{
                $.messager.alert('来自Crm',data.msg,'error');
            }
        }
    });
}