package com.example9.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

	@Autowired
	private HttpSession session;

	/**
	 * 注文「確認」画面を表示する.
	 * 
	 * @param model リクエストスコープ
	 * @return 注文した商品一覧
	 */
	@RequestMapping("")
	public String toOrderConfirmation(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");

		// ログインしていない状態であればログイン画面へ遷移する
		if (userId == null) {
			return "forward:login";
		}

		List<Order> orderList = orderConfirmationService.showOrderList(userId);
		Order order = orderList.get(0);

		order.setTotalPrice(order.getCalcTotalPrice() + order.getTax());
		model.addAttribute("tax", order.getTax());
		model.addAttribute("order", order);

		return "order_confirm";
	}

	/**
	 * 注文「完了」画面を表示する(OrderControllerのorderメソッドへフォワードします).
	 * 
	 * @return 注文「完了」画面.
	 */
	@RequestMapping("/orders")
	public String doOrder() {
		
		return "forward:/order";
	}

	/**
	 * 注文「確認」画面に表示されている商品を注文する.
	 * 
	 * @return 注文確認画面
	 */
	@RequestMapping("/orderAfterConfirm")
	public String toOrderConfirm(Order order, OrderForm form) {
		Order updateOrder = new Order();

		updateOrder.setDestinationName(form.getName());
		updateOrder.setDestinationEmail(form.getEmail());
		updateOrder.setDestinationZipcode(form.getZipcode());
		updateOrder.setDestinationAddress(form.getAddress());
		updateOrder.setDestinationTel(form.getTelephone());
		updateOrder.setDeliveryTime(form.getDeliveryTime());
		updateOrder.setPaymentMethod(form.getPaymentMethod());

		orderConfirmationService.updateOrder(updateOrder);

		return "order_finished";
	}
}
