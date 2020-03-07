package com.example9.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example9.domain.Order;
import com.example9.form.OrderForm;
import com.example9.repository.OrderRepository;

/**
 * 注文処理をするサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private HttpSession session;

	@ModelAttribute
	public OrderForm setUpOrderForm() {
		return new OrderForm();
	}

	/**
	 * 注文前(status=0)の注文を、注文済(status=1または2)へ更新する.
	 */
	public void doOrder() {
		Integer userId = (Integer) session.getAttribute("userId");
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		for (Order order : orderList) {
			orderRepository.updateStatus(order);
		}
	}

}
