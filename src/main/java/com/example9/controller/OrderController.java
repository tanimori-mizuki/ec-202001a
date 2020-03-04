package com.example9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.service.OrderService;

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

	/**
	 * 注文する.
	 * 
	 * @return 注文完了画面
	 */
	@RequestMapping("")
	public String order() {
		orderService.doOrder();
		return "order_finished";
	}

}
