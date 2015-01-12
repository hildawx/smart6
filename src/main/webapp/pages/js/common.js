// 获取地址栏参数值
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

//读取Cookie的方法
function getCookie(key) {
    var arr1 = document.cookie.split('; ');
    for (var i = 0; i < arr1.length; i++) {
        var arr2 = arr1[i].split('=');
        if (arr2[0] == key) {
            return decodeURI(arr2[1]);
        }
    }
}

// 设置Cookie的方法
function setCookie(key, value, t) {
    var oDate = new Date();
    oDate.setDate(oDate.getDate() + t);
    document.cookie = key + '=' + encodeURI(value) + ';expire=' + oDate.toGMTString();
}

//删除cookies 
function delCookie(name) 
{ 
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 1); 
    var cval=getCookie(name); 
    if(cval!=null) 
    document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
} 

// 所有弹窗关闭将窗内的输入框置空
$(".modal").on('hidden.bs.modal', function (e) {
    $(this).find("input").val("");
});

// 