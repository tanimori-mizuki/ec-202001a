package com.example9.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example9.domain.Order;
import com.example9.repository.OrderRepository;

/**
 * 注文確認画面を処理するサービス.
 * 
 * @author suzukikunpei
 *
 */
@Service
public class OrderConfirmationService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 注文確認画面で入力されたユーザ情報を更新する.
	 * 
	 * @param order 注文情報
	 */
	public void updateOrder(Order order) {
		orderRepository.updateByUserId(order);
	}

}
