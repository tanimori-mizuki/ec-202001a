package com.example9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example9.domain.Order;
import com.example9.repository.OrderRepository;

/**
 * カート内を表示するサービス.
 * 
 * @author yuuki
 *
 */
@Service
@Transactional
public class ShowCartListService {
	@Autowired
	private OrderRepository orderRepository;

	/**
	 * カート内商品の表示.
	 * 
	 * @param userId ユーザーID
	 * @return 注文情報（該当情報なしの場合はnull）
	 */
	public List<Order> showCartList(Integer userId) {
		// サービス内で全ての情報を詰める
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);

		// 検索結果がなかったらnullを返す
		if (orderList == null) {
			return null;
		}

		return orderList;
	}
}
