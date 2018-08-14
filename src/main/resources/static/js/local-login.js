//Cookie 
//see: https://github.com/js-cookie/js-cookie

$(function(){
　　　　// 获取cookie的值
　　　　var name = Cookies.get('name');
　　　　var password = Cookies.get('password');
 
　　　　// 将获取的值填充入输入框中
　　　　$('#name').val(name);
　　　　$('#password').val(password); 
　　　　if(name != null && name != '' && password != null && password != ''){
  			// 选中保存秘密的复选框
　　　　		$("#remember_me").attr('checked',true);
		}
});

function rememberMe(){
	
	let name = $("#name").val();
	let password = $("#password").val();
	
	if($("#remember_me").is(":checked")){
		Cookies.set("rmbUser","true",{expires: 7});
		Cookies.set("name",name,{expires: 7});
		Cookies.set("password",password,{expires: 7});
	}else{
		Cookies.set("rmbUser", "false", { expire: -1 });
　　　　 Cookies.set("name", "", { expires: -1 });
　　　　 Cookies.set("password", "", { expires: -1 });
	}
}