package com.example9.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.service.OrderService;
import com.example9.service.SendMailService;

/**
 * 注文処理をするコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private SendMailService sendMailService;

	/**
	 * 注文する.
	 * 
	 * @return 注文完了画面へのリダイレクト（不正アクセスの場合は404エラー画面）
	 */
	@RequestMapping("")
	public String order(HttpServletRequest request) {

		// 不正な画面遷移で当パスに辿り着いた場合、エラーとする
		String url = request.getHeader("REFERER");
		if (!"http://localhost:8080/confirm".equals(url)
				&& !"http://localhost:8080/confirm/orderAfterConfirm".equals(url)) {
			return "/error/404";
		}

		// メールを送信
		sendMailService.sendMail();

		// 注文する
		orderService.doOrder();
		return "redirect:/order/order-finished";
	}

	/**
	 * 注文完了画面を表示する.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("/order-finished")
	public String orderFinished() {
		return "order_finished";
	}

}
