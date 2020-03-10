$(function(){
	
	$("input[name='password']").on("keyup", function(){
		var hostUrl = "http://localhost:8080/register/pass-check-api"
		var password = $("input[name='password']").val();

		$.ajax({
			url: hostUrl,
			type: "GET",
			dataType: "json",
			data:{
				password: password ,
			},
			async: true
		}).done(function(data){
			console.log(data);
			$("#passwordMessage").text(data.passwordMessage);
		})
	});
});