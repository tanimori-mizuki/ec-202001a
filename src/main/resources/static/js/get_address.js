
$(function(){
	$('#search_address').on("click",function(){
		AjaxZip3.zip2addr('zipcode','','address','address');
	});
});

$(function(){
	$("#search_address_again").on("click",function(){
		AjaxZip3.zip2addr('zipcode','','address','address');
	});
});

//zipcodeを使用する方法も記述しましたが、サーバ側に問題が発生することが多い為、コメントアウトします。

//$(function() {
//	// ［住所取得］ボタンクリックで⾮同期処理開始
//	$('#search_address').on("click", function() {
//		$.ajax({
//			url : "http://zipcoda.net/api", // 送信先 WebAPI の URL
//			dataType : "jsonp", // レスポンスデータ形式今回は最後に p をつける
//			data : { // リクエストパラメータ情報
//				zipcode : $('#inputZipcode').val()
//			},
//			async : true
//		// ⾮同期で処理を⾏う
//		}).done(function(data) {
//			// 検索成功時にはページに結果を反映
//			// コンソールに取得した JSON データを表⽰
//			console.dir(JSON.stringify(data));
//			$("#inputAddress").val(data.items[0].address); // 住所欄に住所をセット
//		}).fail(function(XMLHttpRequest, textStatus, errorThrown) {
//			// 検索失敗時には、その旨をダイアログ表⽰しエラー情報をコンソールに記載
//			alert('正しい結果を得られませんでした。');
//			console.log("XMLHttpRequest : " + XMLHttpRequest.status);
//			console.log("textStatus : " + textStatus);
//			console.log("errorThrown : " + errorThrown.message);
//		});
//	});
//});