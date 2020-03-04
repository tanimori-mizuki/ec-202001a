package com.example9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example9.domain.Order;
import com.example9.repository.OrderRepository;

@Service
@Transactional
public class ShowCartListService {
	@Autowired
	private OrderRepository orderRepository;
	
	// サービス内で全ての情報を詰める
	public List <Order>  showCartList (Integer userId) {
		List <Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0); 
		
		// 検索結果がなかったらnullを返す
		if (orderList == null) {
			return null;
		}
		
		return orderList;
	}
}
