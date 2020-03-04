package com.example9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Order;
import com.example9.form.OrderForm;
import com.example9.service.OrderConfirmationService;

/**
 * 注文確認画面を処理するコントローラ.
 * 
 * @author suzukikunpei
 *
 */
@Controller
@RequestMapping("/confirm")
public class OrderConfirmationController {
	
	@ModelAttribute
	public OrderForm setupOrderForm() {
		return new OrderForm();
	}
	
	@Autowired
	private OrderConfirmationService orderConfirmationService;
	
	/**
	 * 注文確認画面を表示する.
	 * @return 注文確認画面
	 */
	@RequestMapping("")
	public String toOrderConfirm(Order order,OrderForm form) {
		Order updateOrder = new Order();
		
		updateOrder.setDestinationName(form.getName());
		updateOrder.setDestinationEmail(form.getEmail());
		updateOrder.setDestinationZipcode(form.getZipcode());
		updateOrder.setDestinationAddress(form.getAddress());
		updateOrder.setDestinationTel(form.getTelephone());
		updateOrder.setDeliveryTime(form.getDeliveryTime());
		updateOrder.setPaymentMethod(form.getPaymentMethod());
		
		orderConfirmationService.updateOrder(updateOrder);
		
		return "order_confirm";
	}
}
