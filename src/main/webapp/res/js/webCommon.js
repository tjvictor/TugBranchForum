function callAjax(url, iTarget, iCallBack, iCallBackParam, iPost, iParams, iLoading) {
    callAjax(url, iTarget, iCallBack, iCallBackParam, iPost, iParams, iLoading, true);
}

function callAjax(url, iTarget, iCallBack, iCallBackParam, iPost, iParams, iLoading, async) {
    var aPost = iPost ? 'POST' : 'GET';
    var usid = user ? user.sid : '';
    if(aPost == 'GET')
        if(iParams)
            iParams = iParams+'&usid='+usid;
        else
            iParams = 'usid='+usid;
    else{
        if(!iParams)
            iParams = {};
        iParams.usid = usid;
    }
    var aTarget = iTarget ? '#' + iTarget: iTarget;
    $(iLoading).css('display', 'block');
    $.ajax({
        async: async,
        type: aPost,
        url: url,
        data: iParams,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        success: function(data, textStatus, jqXHR) {
            if (aTarget) {
                $(aTarget).html(data);
            }
            if (iCallBack) {
                if (iCallBackParam) {
                    eval(iCallBack)(iCallBackParam, data);
                } else {
                    eval(iCallBack)(data);
                }
            }
        },
        error: function(xhr, textStatus) {
            alert(xhr.responseText);
        },
        complete: function(data) {
            $(iLoading).css('display', 'none');
        }
    });
}

var user;
function checkUser() {
    if (Cookies.get("user")) {
        user = jQuery.parseJSON(Cookies.get("user"));
    }
    if (!user) {
        window.location = 'login.html';
        return false;
    } else {
        if (user.roleName != '超级管理员') $('#managePage').css('display', 'none');
        Cookies.set("user", user, {
            expires: 1
        });
    }
    return true;
}

function login() {
    var sid = $('#userNameTxt').val();
    var password = $('#passwordTxt').val();
    if (sid == '' || password == '')
        $('#reminderTxt').text('用户名或密码不可以为空');
    else{
        var parameter = 'sid='+sid+'&password='+password
        callAjax('/websiteService/login', '', 'loginCallback', '', '', parameter, '');
    }
}

function loginCallback(data){
    if (data.status == "ok") {
        var user = {
            "id" : data.callBackData.id,
            "sid": data.callBackData.sid,
            "name" : data.callBackData.name,
            "companyId" : data.callBackData.companyId,
            "companyName" : data.callBackData.companyName,
            "roleId": data.callBackData.roleId,
            "roleName": data.callBackData.roleName,
        }
        Cookies.set("user", user, { expires: 1 });
        window.location = '/index.html';
    }else{
        $('#reminderTxt').text(data.prompt);
    }

}

function logout(){
    Cookies.remove("user");
    callAjax('/websiteService/logout', '', '', '', '', '', '', false);
}

function selectPageFunction(){

}

function showToday() {
    var enabled = 0;
    today = new Date();
    var day;
    var date;
    if (today.getDay() == 0) day = " 星期日";
    if (today.getDay() == 1) day = " 星期一";
    if (today.getDay() == 2) day = " 星期二";
    if (today.getDay() == 3) day = " 星期三";
    if (today.getDay() == 4) day = " 星期四";
    if (today.getDay() == 5) day = " 星期五";
    if (today.getDay() == 6) day = " 星期六";

    date = (today.getFullYear()) + "年" + (today.getMonth() + 1) + "月" + today.getDate() + "日" + day + "";

    return date;
}

