$(function() {
	calc_price();
	
	$("input[name='responsibleCompany']").on("change",function() {
		calc_price();
	});

	$("input:checkbox").on("change",function() {
		calc_price();
	});

	$("select[name='area']").on("change",function() {
		calc_price();
	});

	// 値段の計算をして変更する関数
	function calc_price() {
		var size = $("input[name='responsibleCompany']:checked").val();
		console.log("サイズ");
		console.log(size);
		var topping_count = $("input:checkbox:checked").length;
		console.log("トッピング数");
		console.log(topping_count);
		var curry_num = $("select[name='area'] option:selected").val();
		if (size == "M") {
			console.log("Msize分岐");
			var size_price = $("#priceSizeM").text().replace(/,/g,'')*1;
			var topping_price = $("#toppingUnitPriceSizeM").text() * topping_count;
		} else {
			console.log("Lsize分岐");
			var size_price = $("#priceSizeL").text().replace(/,/g,'')*1;
			var topping_price = $("#toppingUnitPriceSizeL").text() * topping_count;
		}
		var price = (size_price + topping_price) * curry_num;
		$("#totalprice").text(price.toLocaleString());
	}
	;
});