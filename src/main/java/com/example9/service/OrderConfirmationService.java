package com.example9.service;

import java.util.List;

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

	/**
	 * ユーザの注文した商品リストを表示する.
	 * 
	 * @param userId ユーザID
	 * @return ユーザの注文した商品リスト.
	 */
	public List<Order> showOrderList(Integer userId) {
		
		//ShowCartListControllerのtoCartメソッドの部分と共通化すること(後で実施)
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);

		// 検索結果がなかったらnullを返す
		if (orderList == null) {
			return null;
		}

		return orderList;
	}
}
