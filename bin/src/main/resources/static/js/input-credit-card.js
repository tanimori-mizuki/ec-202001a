$(function() {
	
	checkPaymentMethod();

	$("input[name='paymentMethod']").on("change", function(){
		checkPaymentMethod();
	});
	
	function checkPaymentMethod() {
		var paymentMethod = $("input[name='paymentMethod']:checked").val();
　		if(paymentMethod=="2"){
　			$("#cardInfo").show();
　		} else{
　			$("#cardInfo").hide();
　		}
　	};
	
});