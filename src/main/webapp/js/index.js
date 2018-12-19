
/* 登陆 */
function login() {
    /***
     * 1. 获取参数,校验参数
     * 2. 发送Ajax请求
     * 3. 回调函数中:
     *      1) 失败,提示用户
     *      2) 成功,跳转主页
     * */
    var userName = $('#username').val();
    var userPwd = $('#password').val();

    if(isEmpty(userName)){
        alert("用户名为空!");
        return;
    }
    if(isEmpty(userPwd)){
        alert("密码为空!");
        return;
    }

    $.ajax({
        url: ctx +"/user/login",
        type:'post',
        data:{
            userName: userName,
            userPwd: userPwd
        },
        success:function (data) {
            //console.log(data);
            if(data.code==200){
                /***
                 * 存cookie信息
                 * */
                $.cookie("userIdStr",data.result.userIdStr);
                $.cookie("userName",data.result.userName);
                $.cookie("realName",data.result.realName);

                //跳转主页
                window.location.href = ctx +'/main';
            }else{
                alert(data.msg);// 提示错误信息
            }
        }
    });
}