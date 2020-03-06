package com.example9.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
			return "forward:/login/referer";
		}

		List<Order> orderList = orderConfirmationService.showOrderList(userId);
		Order order = orderList.get(0);

		order.setTotalPrice(order.getCalcTotalPrice() + order.getTax());
		model.addAttribute("tax", order.getTax());
		model.addAttribute("order", order);
		
		//クレジットカード情報入力欄の有効期限年リストを作成する
		List<Integer> yearList = new ArrayList<>();
		LocalDate date = LocalDate.now();
		int topOfYear = date.getYear();
		int endOfYear = topOfYear+20;
		for(int i=topOfYear; i<=endOfYear; i++) {
			yearList.add(i);
		}
		model.addAttribute("yearList", yearList);

		return "order_confirm";
	}

	/**
	 * 注文「確認」画面に表示されている商品を注文する.
	 * 
	 * @return 注文確認画面
	 */
	@RequestMapping("/orderAfterConfirm")
	public String toOrderConfirm(@Validated OrderForm form, BindingResult result) {
		
		if(result.hasErrors()) {
			return "order_confirm";
		}
		
		Order updateOrder = new Order();
		
		//注文日に関するオブジェクト生成(注文履歴確認で使用する)
		LocalDate now = LocalDate.now();
		Date orderDate = java.sql.Date.valueOf(now);
		updateOrder.setOrderDate(orderDate);
		
		//formからのパラメータをドメインに移す
		updateOrder.setDestinationName(form.getName());
		updateOrder.setDestinationEmail(form.getEmail());
		updateOrder.setDestinationZipcode(form.getZipcode());
		updateOrder.setDestinationAddress(form.getAddress());
		updateOrder.setDestinationTel(form.getTelephone());
		updateOrder.setDeliveryTime(form.getDeliveryTime());
		updateOrder.setPaymentMethod(form.getPaymentMethodInteger());
		
		//userId取得する
		Integer userId = (Integer) session.getAttribute("userId");
		updateOrder.setUserId(userId);
		
		//合計金額を取得する 
		List<Order> orderList = orderConfirmationService.showOrderList(userId);
		Order order = orderList.get(0);
		updateOrder.setTotalPrice(order.getCalcTotalPrice());
		
		orderConfirmationService.updateOrder(updateOrder);

		return "forward:/order";
	}
}
