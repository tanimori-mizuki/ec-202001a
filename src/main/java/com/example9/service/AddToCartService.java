package com.example9.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example9.domain.Item;
import com.example9.domain.Order;
import com.example9.domain.OrderItem;
import com.example9.domain.OrderTopping;
import com.example9.form.AddToCartForm;
import com.example9.repository.ItemRepository;
import com.example9.repository.OrderItemRepository;
import com.example9.repository.OrderRepository;
import com.example9.repository.OrderToppingRepository;

/**
 * ショッピングカートに商品を追加する業務処理.
 * 
 * @author yuuki
 *
 */
@Service
@Transactional
public class AddToCartService {
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private OrderToppingRepository orderToppingRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private HttpSession session;
	
	
	/**
	 * ショッピングカートの内容をDBに追加する
	 * 
	 * @param form ショッピングカート用フォーム
	 */
	public void addToCart(AddToCartForm form) {
		// userIdを取得
		Integer userId = (Integer) session.getAttribute("userId");
		
		// 非ログインユーザーがカートを表示する際の処理
		if (userId == null) {
			userId = session.getId().hashCode();
		}
		
		
		// userIdで注文前のorderを検索
		List<Order> orderList = orderRepository.findByUserIdAndStatus(userId, 0);

		// OrderItemクラスを作成
		OrderItem orderItem = new OrderItem();

		// カートの中身が空の場合の処理
		if (orderList == null) {
			// Orderオブジェクトを作成
			Order order = new Order();
			// userIdをセット userIdがnullだとインサートできない
			order.setUserId(userId);
			// カートに入れるだけなのでステータスは「0」= 注文前で固定
			order.setStatus(0);
			// nullだとインサートできないので0をセットしておく
			order.setTotalPrice(0);
			// OrderをDBにインサートし、自動採番されたIDを取得
			order = orderRepository.insertOrder(order);
			orderItem.setOrderId(order.getId());
		} else {
			orderItem.setOrderId(orderList.get(0).getId());
		}

		// フォームから送られてきた商品idを元に商品オブジェクトを検索
		Item item = itemRepository.findById(form.getItemId());
		orderItem.setItemId(form.getItemId());
		orderItem.setQuantity(form.getQuantity());
		orderItem.setSize(form.getSize());
		orderItem.setItem(item);

		// order_itemsテーブルに保存
		orderItem = orderItemRepository.insertOrderItem(orderItem);
		
		
		if (form.getToppingId() == null) {
			// NullPointerException回避のため
		} else {
			for (String strToppingId : form.getToppingId()) {
				
				// フォームから送られてくるトッピングidはString型なのでIntegerに変換
				Integer intToppingId = Integer.parseInt(strToppingId);

				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setToppingId(intToppingId);
				orderTopping.setOrderItemId(orderItem.getId());

				// DBのorder_toppingsテーブルに保存
				orderToppingRepository.insertOrderTopping(orderTopping);
			}
		}
	}
	
	/**
	 * ログイン前にカートに追加した内容をログイン後のカートに反映させる.
	 */
	public void addToCartAfterLogin() {
		
		// ログイン前のカートに中身がなければ何もせずreturn 
		List <Order> beforeLoginOrderList = orderRepository.findByUserIdAndStatus(session.getId().hashCode(), 0);
		
		if (beforeLoginOrderList == null) {
			return ;
		}
		
		// useIdを取得
		Integer userId = (Integer) session.getAttribute("userId");
		
		// ログイン後のカートを検索
		List <Order> afterLoginOrderList = orderRepository.findByUserIdAndStatus(userId, 0);
		if (afterLoginOrderList == null) {
			// ログイン後のカートが空の場合OrderドメインのuserIdを更新するだけ
			orderRepository.updateUserId(userId);
		} else {
			// ordersテーブルを削除し、order_itemsテーブルのorder_idをカートに入っているものに更新する
			Order beforeLoginOrder = beforeLoginOrderList.get(0);
			orderRepository.deleteOrderAndUpdateOrderItem(beforeLoginOrder.getId(),userId);
		}
	}
}
