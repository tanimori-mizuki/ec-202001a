package com.example9.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example9.domain.Order;
import com.example9.repository.OrderItemRepository;
import com.example9.repository.OrderRepository;

@Service
@Transactional
public class DeleteFromCartService {
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	/**
	 *　主キーを元にorderItemIdを削除する.
	 * 
	 * @param orderItemId 主キー
	 */
	public void cartDelete(Integer orderItemId) {
		// 削除前に判定
		Integer userId = (Integer) session.getAttribute("userId");
		// 仮の対応　後で直す
		if (userId == null) {
			userId = 10000;
		}
		
		List <Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);
		
		Order order = orderList.get(0);
		
		// 削除処理
		orderItemRepository.deleteById(orderItemId);
		
		// もし最後の1つならOrderも削除
		if (order.getOrderItemList().size() == 1) {
			orderRepository.deleteById(order.getId());
		}
	
	}
}
