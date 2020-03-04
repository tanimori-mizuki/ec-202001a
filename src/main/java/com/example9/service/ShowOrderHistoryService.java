package com.example9.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example9.domain.Order;
import com.example9.repository.OrderRepository;

/**
 * 注文履歴表示を行うサービス.
 * 
 * @author mayumiono
 *
 */
@Service
public class ShowOrderHistoryService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 引数のユーザーIDを用いて注文確定済の注文情報を検索・出力する.
	 * @param userId ユーザーID　
	 * @return　注文情報一覧
	 */
	public List<Order> getOrderHistoryList(Integer userId){
		//OrderRepositoryにて、status=0(注文確定前)か0以外かで条件分岐することから、
		//ここで指定する数値は0以外ならば何でも問題ない
		Integer status = 1;
		return orderRepository.findByUserIdAndStatus(userId, status);
	}
	
	public List<Order> getOrderHistoryDetail(Integer orderId){
		return orderRepository.findByOrderId(orderId);
	}

}
