/**
 * Created by DLHT on 2016-06-16.
 * 常用js存储
 */
/*
    js 版本的trim
 */
String.prototype.trim=function() {
    return this.replace(/(^\s*)|(\s*$)/g,'');
};
/*
js 数组转换为字符串
*/
String.prototype.tostr=function(cutline) {
	//如果数组元素前后无空格，使用简单的即可
	//return this.substr(1,this.length-2).replace(/,/g,',');
	
	//若数据元素前后可能有空格，使用这个
	var str='',arstr=new Array();
	arstr=this.substr(1,this.length-2).split(',');
	for (var i = 0; i < arstr.length; i++) {
		str=str+arstr[i].trim();
		if(i<arstr.length-1){
			str+=cutline;
		}
	}
	return str;
};

/*
    只能输入数字,其他不允许输入.
    min:最小输入数字个数,0:不限制 keyup暂时无法控制，只能在onblur控制
    max:最大输入数字个数,0:不限制
    iszero：首位可否为0，true：可以，false：不可以
 */
String.prototype.onlyNum=function(min,max,zero){
    //也可根据键盘值判断
    //if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))
    var num=this.replace(/\D/g,'');
    if(!zero){
        if(num.length>0&&num.indexOf(0)==0){
            num=num.substr(1,num.length);
        }
    }
    if(max>0){
        if(num.length>max){
            num=num.substr(0,max);
        }
    }
    return num;
}

