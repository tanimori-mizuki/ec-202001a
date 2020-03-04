package com.example9.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example9.domain.Order;
import com.example9.domain.OrderItem;
import com.example9.domain.OrderTopping;
import com.example9.domain.User;
import com.example9.service.ShowOrderHistoryService;

/**
 * 注文履歴表示を行うコントローラ.
 * 
 * @author mayumiono
 *
 */
@Controller
@RequestMapping("/order-history")
public class ShowOrderHistoryController {

	@Autowired
	private ShowOrderHistoryService showOrderHistoryService;

	@Autowired
	private HttpSession session;

	/**
	 * 注文履歴一覧の表示.
	 * 
	 * @param model リクエストスコープ
	 * @return 注文履歴一覧画面
	 */
	@RequestMapping("")
	public String showList(Model model) {

		if (session.getAttribute("user") == null) {
			// ログイン前の場合は、ログインページへ遷移
			return "forward:/login";
		}

		// セッションスコープからユーザーID取得
		User user = (User) session.getAttribute("user");
		Integer userId = user.getId();

		try {
			List<Order> orderList = showOrderHistoryService.getOrderHistoryList(userId);
			model.addAttribute(orderList);
		} catch (Exception e) {
			// 注文履歴がnullの場合は、その旨のメッセージをリクエストスコープに格納する
			String nonOrderMessage = "注文履歴がありません";
			model.addAttribute(nonOrderMessage);
		}
		return "order_history";
	}

	@RequestMapping("/detail")
	public String showDetail(Model model, Integer orderId) {
		List<Order> orderList = showOrderHistoryService.getOrderHistoryDetail(orderId);
		Order order = orderList.get(0);
		model.addAttribute(order);

		// 商品毎の小計を計算する
		List<OrderItem> orderItemList = order.getOrderItemList();
		Map<Integer, Integer> itemPriceMap = new HashMap<>();
		Integer curryPrice = 0;
		Integer toppingsPrice = 0;
		for (OrderItem orderItem : orderItemList) {
			// 本体価格を算出
			if (orderItem.getSize() == 'M') {
				curryPrice = orderItem.getItem().getPriceM();
			} else if (orderItem.getSize() == 'L') {
				curryPrice = orderItem.getItem().getPriceL();
			}
			// トッピング合計額を算出
			List<OrderTopping> toppingList = orderItem.getOrderToppingList();
			if (toppingList.size() == 0) {
			} else if (orderItem.getSize() == 'M') {
				toppingsPrice = toppingList.size() * toppingList.get(0).getTopping().getPriceM();
			} else if (orderItem.getSize() == 'L') {
				toppingsPrice = toppingList.size() * toppingList.get(0).getTopping().getPriceL();
			}
			Integer sum = (curryPrice + toppingsPrice) * orderItem.getQuantity();
			System.out.println(sum);
			// Mapに金額格納(key:orderItemId, value:金額)
			itemPriceMap.put(orderItem.getId(), sum);
		}
		model.addAttribute(itemPriceMap);
		System.out.println(order);
		return "order_history_detail";
	}

}
